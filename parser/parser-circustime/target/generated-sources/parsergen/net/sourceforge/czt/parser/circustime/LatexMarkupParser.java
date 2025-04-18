
/*
  Copyright (C) 2004, 2005, 2006, 2007 Petra Malik
  This file is part of the CZT project: http://czt.sourceforge.net

  The CZT project contains free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License as published
  by the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  The CZT project is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along
  with CZT; if not, write to the Free Software Foundation, Inc.,
  59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package net.sourceforge.czt.parser.circustime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

import net.sourceforge.czt.parser.util.CyclicParseManager;
import net.sourceforge.czt.parser.util.ErrorType;
import net.sourceforge.czt.parser.util.LatexMarkupFunction;
import net.sourceforge.czt.parser.util.LatexMarkupFunctionVisitor;
import net.sourceforge.czt.parser.util.LatexSym;
import net.sourceforge.czt.parser.util.Lexer;
import net.sourceforge.czt.parser.util.CztLexerImpl;
import net.sourceforge.czt.parser.util.LocInfo;
import net.sourceforge.czt.parser.util.LocInfoImpl;
import net.sourceforge.czt.parser.util.LocToken;
import net.sourceforge.czt.parser.util.MarkupDirective;
import net.sourceforge.czt.parser.util.MarkupException;
import net.sourceforge.czt.parser.util.Pair;

import net.sourceforge.czt.parser.z.CyclicParentError;
import net.sourceforge.czt.parser.z.ZParseError;
import net.sourceforge.czt.parser.z.ZParseMessage;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.DefaultSectionParents;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.z.ast.ZSect;
import net.sourceforge.czt.z.util.ZUtils;
import net.sourceforge.czt.util.CztLogger;
import net.sourceforge.czt.z.ast.Directive;
import net.sourceforge.czt.z.ast.DirectiveType;
import net.sourceforge.czt.z.util.Factory;
import net.sourceforge.czt.util.Section;
import net.sourceforge.czt.z.util.ZChar;

import net.sourceforge.czt.session.Dialect;

/**
 * A latex markup parser that looks like a scanner.
 * Instances of this class are usually used after the Latex2Unicode
 * converter.  It preprocesses the output of the converter and updates
 * the markup function appropriately.  It is possible to use the same
 * markup function in the converter if each markup command is used
 * AFTER it is defined in a markup directive.
 */
public class LatexMarkupParser
  extends CztLexerImpl
{
  private static Factory factory_ = new Factory();

  /**
   * The latex to unicode scanner that provides the input.
   */
  private final LatexLexer lexer_;

  /**
   * Section information.
   */
  private final SectionInfo sectInfo_;
  
  private DefaultSectionParents defaultSP_;

  /**
   * The markup function for the current section.
   */
  private LatexMarkupFunction markupFunction_ = null;

  /**
   * All markup functions created so far.
   */
  private final Map<String,LatexMarkupFunction> markupFunctions_ =
    new HashMap<String,LatexMarkupFunction>();

  /**
   * Are we just parsing a section header?
   */
  private boolean sectHead_ = false;

  /**
   * The current section name.
   */
  private String sectName_ = null;

  /**
   * The parents of the current section.
   */
  private String parents_ = null;

  /**
   * The token returned by the last call to method next_token.
   */
  private LocToken symbol_ = null;

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private static final Dialect dialect_ = 
  						Dialect.CIRCUSTIME
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  /**
   * Creates a new latex markup parser that uses the scanner provided.
   * The section information should be able to provide information of type
   * <code> 
   * net.sourceforge.czt.parser.util. LatexMarkupFunction.class</code>.
   * @param lexer
   * @param sectInfo
   */
  public LatexMarkupParser(LatexLexer lexer, SectionInfo sectInfo)
  {
    if (lexer == null || sectInfo == null) throw new NullPointerException();
    lexer_ = lexer;
    sectInfo_ = sectInfo;
    assert dialect_ != null;
    if (!dialect_.equals(sectInfo.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in LatexMarkupParser scanner. " + dialect_.toString() +
    		" expected, but section manager dialect " + sectInfo.getDialect() + " found."); 
    if (!dialect_.equals(lexer.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in LatexMarkupParser scanner. " + dialect_.toString() +
    		" expected, but LatexLexer dialect " + lexer.getDialect() + " found."); 
  }
  
  @Override
  public Dialect getDialect()
  {
  	assert dialect_.equals(lexer_.getDialect()) &&
  			dialect_.equals(sectInfo_.getDialect()) :
  			"dialect = " + dialect_ + "; sectInfo.dialect = " +
  			sectInfo_.getDialect() + "; lexer.dialect = " + lexer_.getDialect();
  	return lexer_.getDialect();
  }

  @Override
  public Source getSource()
  {
    return lexer_.getSource();
  }

  /**
   * Adds the markup function of the given specification
   * to the current markup function.
   *
   * @param parent the name of the parent specification.
   */
  private void addMarkupFunction(String parent)
  {
    if (markupFunction_ == null) {
      markupFunction_ = new LatexMarkupFunction(sectName_);
      markupFunctions_.put(sectName_, markupFunction_);
      lexer_.setMarkupFunction(markupFunction_);
    }
    LatexMarkupFunction markupFunction = markupFunctions_.get(parent);
    if (markupFunction == null) {
      try {
        Key<LatexMarkupFunction> key = new Key<LatexMarkupFunction>(parent, LatexMarkupFunction.class);
        markupFunction = sectInfo_.get(key);
      }
      catch (CommandException exception) {
        String message = "Cannot get LatexMarkupFunction for " + parent
          + "; try to continue anyway for " + dialect_.toString() + " in LatexMarkupParser.";
        CztLogger.getLogger(LatexMarkupFunctionVisitor.class).warning(message);
      }
    }
    if (markupFunction != null) {
      try {
        markupFunction_.add(markupFunction);
      }
      catch (MarkupException e) {
        reportError(e);
      }
    }
  }

  private boolean is(LocToken token, LatexSym sym)
  {
    return token.getName().equals(sym.toString());
  }

  /**
   * <p>
   * Starts the Latex Markup Function calculation transaction in the Section Manager. The
   * transaction allows to capture the dependencies of the LMF - e.g. that it depends on parent
   * LMFs. Because of the "manual" use of LatexMarkupParser (as opposed to using Section Manager
   * commands), we need to manually start and end the transaction.
   * </p>
   * <p>
   * The start of transaction handles issues with redeclared sections as well. See
   * {@link #endLmfTransaction(String)} for more details about the LMF transaction and explicit
   * dependencies.
   * </p>
   * <p>
   * Note that the code wrapped in start/end of transactions for LMF does not throw unchecked
   * exceptions. For this reason there is no need to catch them and cancel the failed transaction.
   * </p>
   * 
   * @param sectName
   *          same as sectName_, but to emphasise the dependency
   * @see #endLmfTransaction(String)
   */
  private void startLmfTransaction(String sectName)
  {
    // set sectName_ first
    assert sectName.equals(sectName_);

    Key<LatexMarkupFunction> lmfKey = 
        new Key<LatexMarkupFunction>(sectName, LatexMarkupFunction.class);

    /*
     * There may be cases when the Latex Markup Function (LMF) is calculated already at the start of
     * transaction. Sometimes it can be because of the pollution of section manager (SM) from
     * previous runs - this should have been removed before running the parsing again. Polluted
     * section managers would generally be caught by other transactions (e.g. ZSect parsing), so it
     * is not that important to be strict about existing objects in SM cache here. 
     * 
     * However, there are cases when we can encounter this case in a single specification (RedeclaredSection). 
     * The parser is required to lex/parse such specification without errors, and leave the check for
     * the typechecker. Thus there is lexing/parsing of two identical section names (and therefore keys).
     * For this case - when we encounter the second (redeclared) section, we remove the LMF from SM if 
     * it is already cached, and start a new transaction for its calculation. We will have a case
     * when the second LMF "overwrites" the first LMF via remove-endTransaction.
     * 
     * If we do not do such "overwriting", we would have needed to make the RedeclaredSection a
     * parsing error and stop lexing/parsing altogether (e.g. via Exception). Current solution allows
     * us to finish lexing and postpone the check to typechecker.
     */
    if (sectInfo_.isCached(lmfKey))
    {
      sectInfo_.removeKey(lmfKey);
    }
    
    /*
     * Force start the transaction - we cannot have it started already. The latex markup parser is
     * used within the Z parser, and not within the command. Therefore we should not have an
     * existing transaction on LMF. Therefore do a strict startTransaction(), instead of
     * ensureTransaction().
     * 
     * If the ZSect is Unicode, or has been parsed before, this parser will never be used - the 
     * LatexMarkupCommand will be used instead, which handles its own LMF transactions. If parsing 
     * is started from within the LatexMarkupCommand, the original LMF transaction is postponed 
     * in favor of ZSect transaction (see LatexMarkupCommand for details). 
     */
    sectInfo_.startTransaction(lmfKey);
    
  }
  
  /**
   * <p>
   * Ends the transaction for Latex Markup Function calculation in the Section Manager. The
   * transaction is ended immediately after calculation of the LMF. The LMF is then populated with
   * Latex directives during lexing. This means that the LMF can be empty when first put in the
   * section manager, and filled with content later. Such approach is necessary to ensure correct
   * transactions in the section manager. Otherwise, Parsing and Lexing can overlap (depending on
   * the buffer), and transactions would fail.
   * </p>
   * <p>
   * To accommodate for the short-circuiting of LMF transaction, we add an explicit dependency on
   * the ZSect for the LMF. This is to signal that the ZSect may contain Latex directives which will
   * constitute this LMF.
   * </p>
   * <p>
   * The same approach (end transaction after initial resolution of an object) is used for other
   * components in the parser as well, e.g. OpTable, JokerTable, etc. This is necessary because of a
   * complicated parsing protocol, and is a compromise to achieve transactional structure to some
   * level. Otherwise the transactions would overlap. To achieve better confidence in the
   * dependencies, some dependencies are added explicitly (e.g. that LMF depends on its ZSect).
   * </p>
   * <p>
   * With LMF (also with other Parser objects, e.g. OpTable, JokerTable), we have a bi-directional
   * dependency between the LMF and ZSect (LMF &harr; ZSect). This means that LMF depends on ZSect
   * for its content, and ZSect depends on LMF for instructions how to interpret Latex symbols. We
   * define one direction via explicit dependency - LMF depends on its ZSect. The backwards
   * dependency (ZSect depends on its LMF) is done in the Parser, where ZSect transaction is ended.
   * Note that this introduces unnecessary dependency on LMF for non-Latex ZSects, however we
   * believe it to be a conservative compromise to achieve confidence in dependencies.
   * </p>
   * 
   * @param sectName
   *          same as sectName_, but to emphasise the dependency
   * @see #startLmfTransaction(String)
   */
  private void endLmfTransaction(String sectName) {

    // set sectName_ first
    assert sectName.equals(sectName_);
    assert markupFunction_ != null;

    Key<LatexMarkupFunction> lmfKey = 
      new Key<LatexMarkupFunction>(sectName, LatexMarkupFunction.class);

    /*
     * Add explicit dependency for LMF on its ZSect. This is because the ZSect is used afterwards to
     * populate the current LMF.
     */
    sectInfo_.endTransaction(lmfKey, markupFunction_, 
        Collections.singleton(new Key<ZSect>(sectName_, ZSect.class)));
        
    /*
     * Note that we need to signal to the ZSect somehow to mark the dependency on this LMF. One
     * approach could be to do a sectInfo_.get(lmfKey) - it will mark a dependency in the section
     * manager on the LMF key, so if the outer transaction is that of the ZSect, it will capture the
     * dependency. 
     * 
     * However, due to the way lexing and parsing overlap, it is often the case that
     * lexing (and LMF calculation) has already finished by the time parsing starts (and thus ZSect
     * transaction starts). For that reason, we cannot rely on sectInfo_.get() to mark the
     * dependency, and it is indicated as an explicit dependency in the Parser. When ending the
     * transaction for the ZSect, we mark that it depends on its LMF.
     */
  }

  @Override
  public LocToken next()
    throws IOException
  {
    final LocToken token = lexer_.next();
    if (token == null) {
      /*
       * Previously the current markup function (LMF) was put into the section manager at the end of
       * lexing (when token is null). Now the LMF is put into the section manager at the time of
       * creation. This is needed because calculation of LMF may overlap with parsing - it depends
       * on the buffer. We want to have LMF in the section manager as soon as possible, to ensure
       * correct layering of section manager transactions.
       */
      return token;
    }
    final boolean isStartOfAnonymousSpec =
      ! sectHead_ && sectName_ == null &&
        (is(token, LatexSym.CHAR_MARKUP) ||
         is(token, LatexSym.WORD_MARKUP) ||
         is(token, LatexSym.INWORD_MARKUP) ||
         is(token, LatexSym.PREWORD_MARKUP) ||
         is(token, LatexSym.POSTWORD_MARKUP) ||
         is(token, LatexSym.UNICODE));
    if (isStartOfAnonymousSpec) {
      // we are parsing an anonymous specification
      sectName_ = Section.ANONYMOUS.getName();
      
      	try {
          defaultSP_ =
          	sectInfo_.get(new Key<DefaultSectionParents>(sectName_,
          							DefaultSectionParents.class));
        }
        catch (CommandException e) {
          e.printStackTrace();
          //TODO: check this. why not report the error as a scan exception?
          return token;
        } 
      
      // start the LMF transaction, add necessary parents, and end the transaction
      // see comments on startLmfTransaction and endLmfTransaction for details
      // Note no unchecked exceptions until endLmfTransactions, so we do not need
      // to handle anything and cancel failed transaction.
      startLmfTransaction(sectName_);

      Set<String> parentSet = defaultSP_.defaultParents(sectName_);

  	  // For anonymous sections, the default parents depend on the extension. 
	  // They all take standard_toolkit as a default parent plus their language toolkit
      parents_ = ZUtils.parentsAsString(parentSet);
      
      for(String parent : parentSet)
      {
      	addMarkupFunction(parent);
      }
      
      // end transaction now - will fill the contents during lexing afterwards
      // see comments on startLmfTransaction and endLmfTransaction for details
      endLmfTransaction(sectName_);
    }
    if (sectHead_) { // we are just parsing a section header
      if (is(token, LatexSym.END)) { // end of section header
        sectName_ = ZUtils.trimNLCharAware(sectName_);

		try {
          defaultSP_ =
          	sectInfo_.get(new Key<DefaultSectionParents>(sectName_,
          							DefaultSectionParents.class));
        }
        catch (CommandException e) {
          e.printStackTrace();
          //TODO: check this. why not report the error as a scan exception?
          return token;
        } 

        // start the LMF transaction, add necessary parents, and end the transaction
        // see comments on startLmfTransaction and endLmfTransaction for details
        // Note no unchecked exceptions until endLmfTransactions, so we do not need
        // to handle anything and cancel failed transaction.
        startLmfTransaction(sectName_);
        
        markupFunction_ = new LatexMarkupFunction(sectName_);
        markupFunctions_.put(sectName_, markupFunction_);
        lexer_.setMarkupFunction(markupFunction_);
        
        
        Set<String> defaultParentSet = defaultSP_.defaultParents(sectName_);
        for(String p : defaultParentSet)
        {
        	addMarkupFunction(p);
        }

		// now process each collected parent.
        if (parents_ != null) {
          String[] parents = parents_.split(",");
          List<String> parentList = new ArrayList<String>();
          for (String parent : parents) {
            parent = ZUtils.trimNLCharAware(parent);
            if (parent != null && ! parent.equals("")) {
              parentList.add(parent);
            }
          }
          
          // use the cyclic manager to get valid parents avoiding cyclic recursion
          CyclicParseManager cyclicMan = CyclicParseManager.getManager(sectInfo_);
          List<String> validParents = cyclicMan.getValidParentNames(sectName_, parentList); 
          try {
            for (String parent : validParents) {
              addMarkupFunction(parent);
            }
          } finally {
            // mark section inactive and report cycles
            List<List<String>> cycles = cyclicMan.visitedParents(sectName_);
            for (List<String> cycle : cycles) {
              // report found cycles, if any, as warnings
              reportParentCycle(cycle, parentList);
            }
          }
          
        }
        
        // end transaction now - will fill the contents during lexing afterwards
        // see comments on startLmfTransaction and endLmfTransaction for details
        endLmfTransaction(sectName_);
        sectHead_ = false;
      }
      else if (is(token, LatexSym.SECTION)) { // section token
        // start parsing section name
        sectName_ = "";
      }
      else if (is(token, LatexSym.PARENTS)) { // parents token
        // start parsing parents
        parents_ = "";
      }
      else {
        if (parents_ != null) {
          parents_ += token.spelling();
        }
        else if (sectName_ != null) {
          sectName_ += token.spelling();
        }
      }
    }
    else if (is(token, LatexSym.SECT)) { // begin of a section header
      /*
       * Previously at the start of a section header, there was a clause to add existing markup
       * function (from previous section?) to the section manager. This has now been updated to put
       * the markup function into the section manager as soon as it is created.
       */
      sectHead_ = true;
      parents_ = null;
      sectName_ = null;
    }
    else if (is(token, LatexSym.CHAR_MARKUP)) {
      Directive directive = parseCharMarkupDirective(token.spelling(),
                                                     token.getLocation());
      if (directive != null) {
        try {
          markupFunction_.add(getDialect(), directive);
        }
        catch (MarkupException e) {
          reportError(e);
        }
      }
      return next();
    }
    else if (is(token, LatexSym.WORD_MARKUP)) {
      parseWordMarkup(DirectiveType.NONE, token.getLocation());
    }
    else if (is(token, LatexSym.INWORD_MARKUP)) {
      parseWordMarkup(DirectiveType.IN, token.getLocation());
    }
    else if (is(token, LatexSym.PREWORD_MARKUP)) {
      parseWordMarkup(DirectiveType.PRE, token.getLocation());
    }
    else if (is(token, LatexSym.POSTWORD_MARKUP)) {
      parseWordMarkup(DirectiveType.POST, token.getLocation());
    }
    check(symbol_, token);
    if (token.spelling() != null) {
      symbol_ = token;
    }
    return token;
  }
  
  private void reportParentCycle(List<String> cycle, List<String> parents)
  {
    Pair<String, String> render = CyclicParseManager.renderParseParentCycle(cycle);
    String cycleParent = render.getFirst();
    String cycleStr = render.getSecond();
    
    int parentIndex = 0;
    for (String parent : parents) {
      if (cycleParent.equals(parent)) {
        // found the parent - report cycle with its location as a warning
        // note using dynamic location resolving errors here
        CyclicParentError.reportCyclicParentNoLoc(sectInfo_, getSource(), 
            sectName_, cycleParent, parentIndex, cycleStr);
        parentIndex++;
        // do not break, because several parents with the same name could be
        // listed - we need to report the same cycle for all of them
      }
    }
    
    if (parentIndex == 0) {
      // no applicable parent found? 
      // still report - using dummy location
      CyclicParentError.reportCyclicParent(sectInfo_, getSource(), cycleStr, 
          new LocInfoImpl(getDialect(), getSource().getName(), 0, 0));
    }
  }

  private void check(LocToken t1, LocToken t2)
  {
    if (t1 != null && t2 != null) {
      if (is(t1, LatexSym.UNICODE) && is(t2, LatexSym.UNICODE)) {
        String s1 = t1.spelling();
        String s2 = t2.spelling();
        if (s1 != null && s1.length() > 0 &&
            s2 != null && s2.length() > 0) {
          final ZChar[] zchars1 = ZChar.toZChars(s1);
          final ZChar[] zchars2 = ZChar.toZChars(s2);
          ZChar c1 = zchars1[zchars1.length - 1];
          ZChar c2 = zchars2[0];
          final boolean c1IsLetterOrDigit =
            ZChar.isDigit(c1) || ZChar.isLetter(c1);
          final boolean c2IsLetterOrDigit =
            ZChar.isDigit(c2) || ZChar.isLetter(c2);
          final boolean c1IsDeltaOrXi =
            ZChar.DELTA.equals(c1) || ZChar.XI.equals(c1);
          final boolean cond =
            c1IsLetterOrDigit && c2IsLetterOrDigit && 
              ! c1IsDeltaOrXi;
          if (cond) {
            ZParseError.report(sectInfo_,
                               getSource(),
                               ErrorType.WARNING,
                               ZParseMessage.MSG_POSSIBLE_MISSING_SPACE,
                               new Object[0],
                               t2.getLocation());
          }
        }
      }
    }
  }

  private void parseWordMarkup(DirectiveType type, LocInfo location)
    throws IOException
  {
    String name = parseName();
    String latex = parseUnicode();
    Directive directive = factory_.createDirective(name, latex, type);
    directive.getAnns().add(factory_.createLocAnn(location.getSource(),
                                                  location.getLine(),
                                                  null));
    try {
      markupFunction_.add(getDialect(), directive);
    }
    catch (MarkupException e) {
      reportError(e);
    }
  }

  private String parseName()
    throws IOException
  {
    LocToken token = lexer_.next();
    if (is(token, LatexSym.NAME)) {
      return token.spelling();
    }
    CztLogger.getLogger(LatexMarkupParser.class).severe("Error while parsing markup directive " + token + " for " + dialect_.toString() + " in LatexMarkupParser.");
    return null;
  }

  private String parseUnicode()
    throws IOException
  {
    StringBuilder result = new StringBuilder();
    LocToken token = lexer_.next();
    while (token != null &&
           ! is(token, LatexSym.END_MARKUP)) {
      result.append(token.spelling());
      token = lexer_.next();
    }
    return result.toString();
  }

  public static Directive parseCharMarkupDirective(String directive,
                                                   LocInfo loc)
  {
    String[] splitted = directive.split("[ \t]+");
    final int expectedLength = 3;
    if (splitted.length == expectedLength) {
      DirectiveType type = DirectiveType.NONE;
      String name = splitted[1];
      if ("%%Zprechar".equals(splitted[0])) {
        type = DirectiveType.PRE;
      }
      else if ("%%Zpostchar".equals(splitted[0])) {
        type = DirectiveType.POST;
      }
      else if ("%%Zinchar".equals(splitted[0])) {
        type = DirectiveType.IN;
      }

      if (splitted[2].startsWith("U+")) {
        final int beginString = 2;
        final int endString = 6;
        String hexValue = splitted[2].substring(beginString, endString);
        final int hexBase = 16;
        int decimal = Integer.parseInt(hexValue, hexBase);
        char[] chars = Character.toChars(decimal);
        String unicode = new String(chars);
        Directive d = factory_.createDirective(name, unicode, type);
        d.getAnns().add(factory_.createLocAnn(loc.getSource(),
                                              loc.getLine(),
                                              null));
        return d;
      }
      else if (splitted[2].startsWith("U-")) {
        final int beginString = 2;
        final int endString = 10;
        String hexValue = splitted[2].substring(beginString, endString);
        final int hexBase = 16;
        int decimal = Integer.parseInt(hexValue, hexBase);
        char[] chars = Character.toChars(decimal);
        String unicode = new String(chars);
        Directive d = factory_.createDirective(name, unicode, type);
        d.getAnns().add(factory_.createLocAnn(loc.getSource(),
                                              loc.getLine(),
                                              null));
        return d;
      }
      CztLogger.getLogger(LatexMarkupParser.class).severe(
      	"WARNING: Cannot parse " + directive + " for " + dialect_.toString() + " in LatexMarkupParser.");
      return null;
    }
      CztLogger.getLogger(LatexMarkupParser.class).severe(
      	"WARNING: Cannot parse " + directive + " for " + dialect_.toString() + " in LatexMarkupParser.");
    return null;
  }

  private void reportError(MarkupException e)
  {
    MarkupDirective d1 = e.getMarkupDirective1();
    MarkupDirective d2 = e.getMarkupDirective2();
    Dialect d = e.getDialect();
    if (!d.equals(dialect_))
      throw new IllegalArgumentException("Incompatible dialects in LatexMarkupParser. " + dialect_.toString() +
    		" expected, but MarkupException dialect " + d + " found.");
    Object[] args = { d1.getCommand(), d1, d2, d };
    LocInfo location = new LocInfoImpl(getDialect(), getSource().toString(),
                                       // d2 can be null
                                       (d2 != null ? d2 : d1).getLine().intValue(),
                                       -1,
                                       -1,
                                       -1);
    ZParseError.report(sectInfo_,
                       getSource(),
                       ErrorType.ERROR,
                       ZParseMessage.MSG_LATEX_COMMAND_DEFINED_TWICE,
                       args,
                       location);
    args = null;
  }

  

  public interface LatexLexer
    extends Lexer
  {
    void setMarkupFunction(LatexMarkupFunction markupFunction);
  }
}
