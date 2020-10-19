

package net.sourceforge.czt.parser.ozpatt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java_cup.runtime.Symbol;

import net.sourceforge.czt.parser.util.DebugUtils;
import net.sourceforge.czt.parser.util.LocInfo;
import net.sourceforge.czt.parser.util.LocInfoImpl;
import net.sourceforge.czt.parser.util.LocInt;
import net.sourceforge.czt.parser.util.LocString;
import net.sourceforge.czt.parser.util.ScanException;
import net.sourceforge.czt.parser.util.TokenStack;
import net.sourceforge.czt.parser.util.CztScanner;
import net.sourceforge.czt.parser.util.CztScannerImpl;



import net.sourceforge.czt.session.Dialect;

/**
 * <p>
 *   Looks ahead in the token stream to resolve some Z ambiguities.
 * </p>
 *
 * <p>As described in the ISO Z standard (Section 8.4, p37), the Z
 * grammar has several ambiguities.  This class 'buffers' the token
 * stream, so that it can look ahead in the token stream if necessary,
 * to help resolve the ambiguities.</p>
 *
 * <p>For example, in {x,y,z...}, if the x,y,z is followed by
 * ':', then it is part of a declaration (a set comprehension) and
 * declares new variables x,y,z, otherwise it is a set extension, and
 * x,y,z must already have been declared.  To resolve this, whenever
 * we come to a DECORWORD, this class looks ahead over (COMMA, name)
 * pairs where name is either a single DECORWORD or an operator name, to see
 * if they are followed by a COLON (:) token.  If they are, it returns
 * those names as DECLNAME tokens rather than DECORWORD tokens.</p>
 *

 * This class also resolves ambiguities in Object-Z. To resolve the
 * problem of whether the first name seen after a class name is a
 * reference to an inheriting class or a reference to a name, we use
 * lookahead. If the token after the name is ::= or ==, the smart
 * scanner returns a DEFNAME instead of a DECORWORD. If the token is
 * \sdef, we return an OPNAME instead of a DECORWORD.

 */
class SmartScanner extends CztScannerImpl
{
  //indicates whether the previous token is a named box token
  //i.e. SCH, GENSCH, CLASS, OPSCH, ZPROOF
  private boolean inBoxName = false;

  
  
  private TokenStack dumb_;

  
  private final String sourceName_;
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.OZPATT
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;

  SmartScanner(String sourceName, CztScanner dumbscanner)
  {
    sourceName_ = sourceName == null ? "unknownSource" : sourceName;
    dumb_ = new TokenStack(dumbscanner);
    assert dialect_ != null;
    if (!dialect_.equals(dumbscanner.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in SmartScanner. " + dialect_.toString() +
    		" expected, but CztScanner " + dumbscanner.getDialect() + " found."); 
  }
  
  @Override
  public Dialect getDialect()
  {
  	assert dialect_.equals(dumb_.getDialect()) : "dialect = " + dialect_ + "; dumbScanner.dialect = " + dumb_.getDialect();
  	return dumb_.getDialect();
  }

  protected LocInfo getLocation(Symbol symbol)
  {
    if (symbol.value instanceof LocInfo) {
      return (LocInfo) symbol.value;
    }
    if (symbol.value instanceof LocString) {
      LocString s = (LocString) symbol.value;
      return s.getLocation();
    }
    if (symbol.value instanceof LocInt) {
      LocInt i = (LocInt) symbol.value;
      return i.getLocation();
    }
    return new LocInfoImpl(getDialect(), sourceName_, symbol.left, symbol.right);
  }

  protected boolean checkEOF(Symbol currsym, LinkedList<Symbol> tokens) throws ScanException
  {
    boolean result = true;
    if (currsym.sym == Sym.EOF)
    {
      result = false;
      final int tks = tokens.size();
      final Symbol last = tokens.get(tks > 1 ? tks-2 : tks-1);
      final Symbol first = tokens.getFirst();
      final String message = "Hit EOF whilst trying to find end of a token during smart scanning. Lookahead size = " + tks
              + "; stack token before EOF = " + get_sym_info(last) + "; lookahead starting token = " + get_sym_info(first) 
              + "; dialect = " + dialect_.toString();
      System.err.println(message);
      throw new ScanException(dialect_, message, get_sym_info(first), getLocation(first));
    }
    return result;
  }
  
  @Override
  protected Class<?> getSymbolClass()
  {
    return Sym.class;
  }
  
  @Override
  public Symbol next_token()
    throws Exception
  {
    Symbol result = dumb_.pop();
    if (result.sym == Sym.DECORWORD && inBoxName) {
      //don't look ahead if the previous token was a box token
      inBoxName = false;
    }
    else if (result.sym == Sym.DECORWORD && !inBoxName)
    {

      //if this is a colon decl, convert the DECORWORD to a DECLWORD
      if (isColonDecl()) 
      {
        result.sym = Sym.DECLWORD;
      }

      else
      {  
        LinkedList < Symbol > tokens = new LinkedList < Symbol >();
        Symbol currsym = dumb_.pop();
        tokens.addLast(currsym);
        //skip any newlines
        while (currsym.sym == Sym.NL) {
          currsym = dumb_.pop();
          tokens.addLast(currsym);
        }
        //skip any formal parameters
        if (currsym.sym == Sym.LSQUARE) {
          while (currsym.sym != Sym.RSQUARE) {
            currsym = dumb_.pop();
            tokens.addLast(currsym);
          }
          currsym = dumb_.pop();
          tokens.addLast(currsym);
        }
        //if the next token is a DEFFREE or DEFEQUAL, return a DEFNAME
        if (currsym.sym == Sym.DEFFREE || currsym.sym == Sym.DEFEQUAL) {
          result.sym = Sym.DEFNAME;
        }
        //if the next token is a SDEF, return an OPNAME
        else if (currsym.sym == Sym.SDEF) {
          result.sym = Sym.OPNAME;
        }
        pushList(tokens, false);
      }

    }
    else if (result.sym == Sym.SCH ||

             result.sym == Sym.CLASS ||
             result.sym == Sym.OPSCH ||

             result.sym == Sym.GENSCH) 
    {
        inBoxName = true;
        
    }
    
    return result;
  }



  private boolean isColonDecl()
    throws Exception
  {
    boolean isColonDecl = false;
    boolean doLookahead = true;
    LinkedList < Symbol > tokens = new LinkedList < Symbol >();
    Symbol currsym;

    do {
      currsym = dumb_.pop();
      tokens.addLast(currsym);
    }
    while (currsym.sym == Sym.NL);
 
    while (currsym.sym == Sym.COMMA && doLookahead) {
      currsym = dumb_.pop();
      tokens.addLast(currsym);
      doLookahead = false;
      while(currsym.sym == Sym.DECORWORD || currsym.sym == Sym.ARG ||
            currsym.sym == Sym.LISTARG || currsym.sym == Sym.DECLWORD ||
	    currsym.sym == Sym.NL) {
        currsym = dumb_.pop();
        tokens.addLast(currsym);
        doLookahead = true;
      }
    }
    if (currsym.sym == Sym.COLON && doLookahead) {
      isColonDecl = true;
    }
    pushList(tokens, isColonDecl);
    return isColonDecl;
  }
  
  private void pushList(List < Symbol > tokens, boolean isColonDecl)
  {
    for (int i = tokens.size() - 1; i >= 0; i--) {
      Symbol symbol = tokens.get(i);
      if (symbol.sym == Sym.DECORWORD  && isColonDecl) {
        symbol.sym = Sym.DECLWORD;
      }
      else {
      }
      dumb_.push(symbol);
    }
  }
  
  
  
  private Map< Object, String > symbolMap_ = DebugUtils.getFieldMap(Sym.class);  
  
  /**
   * Returns the String representation for the given Symbol sym field.
   * That is, the declared name for that symbol. This declared name can
   * then be used to lookup the Token interface enum implementations for
   * the various extensions. 
   */
  private String get_sym_name(Symbol symbol)
  { 
    String sym = symbolMap_.get(Integer.valueOf(symbol.sym));
    return sym;
  }

  private String get_sym_info(Symbol symbol)
  {
    String result = get_sym_name(symbol);    
    if (symbol.value != null) {
      result += "(" + symbol.value + ")";
    }
    return result;
  }
}
