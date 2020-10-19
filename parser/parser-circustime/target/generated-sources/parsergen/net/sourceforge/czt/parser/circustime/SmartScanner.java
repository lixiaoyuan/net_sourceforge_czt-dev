

package net.sourceforge.czt.parser.circustime;

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


import java.util.ListIterator;
import java.util.Set;

import net.sourceforge.czt.z.ast.InStroke;
import net.sourceforge.czt.z.ast.NextStroke;
import net.sourceforge.czt.z.ast.NumStroke;
import net.sourceforge.czt.z.ast.OutStroke;
import net.sourceforge.czt.z.ast.Stroke;
import net.sourceforge.czt.z.ast.ZStrokeList;

import net.sourceforge.czt.parser.util.Pair;
import net.sourceforge.czt.parser.util.Decorword;

import net.sourceforge.czt.parser.circus.CircusSymMap;


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

 */
class SmartScanner extends CztScannerImpl
{
  //indicates whether the previous token is a named box token
  //i.e. SCH, GENSCH, CLASS, OPSCH, ZPROOF
  private boolean inBoxName = false;


  private static final int DEBUG_FEW    = 000;
  private static final int DEBUG_FINE   = 100;
  private static final int DEBUG_FINER  = 200;
  private static final int DEBUG_FINEST = 300;
  //private static final int DEBUG_ALL    = 400;  
  private int debugLevel = DEBUG_FINE;
  
  /**
   * Indicates whether the previous token is CIRCBEGIN - that is, if a
   * basic process is starting -, since communication only take place
   * within an action declaration
   */
  private boolean inCircusBasicProcess = false;
  
  /**
   * Indicates whether the previous token is CIRCUSACTION - that is, if a
   * BasicProcess defined with multiple LaTeX environments has passed a 
   * begin{circusaction}.
   */
  private boolean inCircusActionEnv = false;
  
  /**
   * After their names, actions MUST have a CIRCDEF token, which is 
   * marked by this flag. That is, it is true whenever we have started
   * a basic process and have encountered a CIRCDEF. It is switched off
   * either when CIRCEND (i.e. end of basic process) or END
   * (i.e. end of a general paragraph) is reached. The former happens
   * when declaring basic processes within a single circus latex environment,
   * whereas the latter happens when using multiple circusaction latex environments
   * to declared basic processes. 
   * 
   * Whenever both inCircusBasicProcess and inActionDef are enabled, and
   * the next token is a DECORWORD, then, just like in colon declaration for Z,
   * we need to lookahead to see whether the DECORWORD is part of a communication
   * or not. That is, we need to lookahead until the flag is switched off, in which
   * case nothing changes; or we find a PREFIXTHEN token, in which case the DECORWORD
   * is rebranded as CIRCUSNAME and the remainder fileds are parsed appropriately.
   */ 
  private boolean inActionDef = false;
  
  /**
   * Each communication can only have one channel name. Nevertheless, because Circus
   * (and CSP) allows expressions with DECORWORDs as outputs or input predicates, we 
   * may have DECORWORDs in beteween the CHANNELNAME and the PREFIXTHEN token. Thus,
   * this flag enables us to avoid capturing those as CHANNELNAMEs.
   *
   * ex: c?x!(x+1)?z \prefixcolon (z > x).y \then \Skip
   *
   * is tokenised as:
   *
   * CHANNELNAME("c"), 
   * INSTROKE, 
   * FIELDNAME("x"),
   * OUTSTROKE,
   * LPAREN,
   * DECORWORD("x"),
   * ....
   * FIELDNAME("z"),
   * PREFIXCOLON,
   * LPAREN
   * DECORWORD("z")
   * ...
   * RPAREN
   * CHANNELDOT
   * FIELDNAME("y")
   * PREFIXTHEN
   * SKIP
   *
   * Also note that because of such expressions, one could have 
   * decorated names (as Z expressions!) in communication fields:
   *
   * ex: c.(x?)!(y!)!(z?) \then \Skip
   */
  private boolean waitingPrefixThen = false;
  
  /** the count on the number of strokes per field name changed must match.
   * otherwise, we have more field names than strokes, which is a problem.
   * this may happen whenever a expression in an output field is not 
   * parenthesised. For input prefix restriction, this is not a problem 
   * because the parser can add the spurious/erroneous production
   * INSTROKE:in FIELDNAME:dw PREFIXCOLON error:e . For output/dot prefixing, 
   * and input without PREFIXCOLON, we need to make this count.
   *
   * ex: without this cound "c!n1+1?x \then \Skip" is wrongly tokenised as:
   *
   *CHANNELNAME(c)
   *OUTSTROKE
   *FIELDNAME(n1)
   *FIELDNAME(+)
   *NUMERAL(1)
   *INSTROKE
   *FIELDNAME(x)
   *PREFIXTHEN
   *CIRCSKIP
   *
   * and later parsed as one OutField(ApplExpr(ApplExpr(ApplExpr(n1, +), 1?), x))
   * that is: c!(((n1~(+))~1?)~x), which the typechecker will clearly give an error.
   *
   * we try to catch more cases at the scanning because we know there was more
   * FIELDNAME tokens than XXXSTROKE tokens. That is, we count strokes and fields
   * to see if they match. Note that expressions count as one output field.
   */
  private int strokeCount = 0;
  private int fieldCount = 0;
  
  /**
   * DECORWORDs within a BasicChannelSetExpr must be analysed just like in 
   * a communication of a prefixing action. That is because a BasicChannelSetExpr
   * contains a list of communications.
   */
  private boolean inBasicChannelSet = false;
  
  /**
   * Similar to waitingPrefixThen, we must mark that DECORWORD within predicates of 
   * communications do not need to be smart lexed. That is, if we find
   * 
   * \lchanset ... c?x?y \prefixcolon (PRED), d?y \rchanset 
   * 
   * any DECORDWORD between prefixcolon and "," is to be left alone by smart scanning. 
   * We could have kept the same flag for handling (PRED) but added specific ones for 
   * communication (waitingPrefixThen) and channelset (waitingNextChanSetComm) as they
   * have quite different logic in what/how they are triggered. See commBracketDepth.
   */
  private boolean waitingNextChanSetComm = false;
  
  /**
   * This counts the bracket numbers in c?x \prefixcolon (....) so that at depth 0,
   * commas are interpreted as real separators of communications rather than part 
   * of the prefix colon predicate
   */
  private int commBracketDepth = 0;

  /**
   *  Set of Z Symbol table names. Note that because of the order they appear in 
   *  the Parser.xml, some numbers are actually different in Z and Circus! 
   *  Ex: 73 in Z is TEXT, and in Circus CIRCREFINES; whereas in Circus TEXT is 130
   * 
   *  One way to simplify this would be to impose an order for the symbols in Parser.xml
   *  However, this compromises precedences for CUP (I think) - so we use this solution here.
   * 
   *  These names are used to know whether to carry looking ahead or not.
   *  private static final Set< String > Z_SYMBOL_NAMES = SymMap.getMap().keySet();
   * 
   *  NOTE: do it differently - use Circus Only symbol names. This shall avoid 
   *        any need to change anything in the future.
   */
  private static final Set< String > CIRCUS_SYMBOL_NAMES_ONLY = CircusSymMap.CIRCUS_SYMBOL_NAMES_ONLY;
  
  
  private TokenStack dumb_;

  
  private final String sourceName_;
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.CIRCUSTIME
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
      
      // if within a basic process, within an action definition, and
      // haven't yet found a DECORWORD that became a CHANNELNAME token
      // (i.e. !waitingPrefixThen), then lookahead for potential communication.
      // 
      // This is important to avoid multiple DECORWORD being changed as 
      // CHANNELNAMEs. This may happen if we have communication fields 
      // with parenthesised expressions, or channels with generic actuals.
      //
      // pass the result symbol because we may need to change its DECORWORD representation.
      // ex: DECORDWORD("c?") becomes CHANNELNAME("c") + INSTROKE
      //
      if (inCircusBasicProcess && inActionDef && 
          !waitingPrefixThen && isChannelName(result))
      {
        debug("\tIn DECORWORD of a basic process after action def", DEBUG_FINER);
        
        // unfortunately, in the case of processes defined within the same
        // circus environment, there is no effecient way of discerning when
        // the action definition has finished. On the other hand, if using
        // circusaction environments, we can know when they finish by looking
        // for a END symbol. See isChannelName for details
        
        debugFlags(result, DEBUG_FINEST);        
        assert checkChannelName(result) : "not valid channel name transformation in smart scanner at prefixing action \n\t" + get_sym_info(result);
      }
      // if within a basic channel set enumeration, then check for channel names 
      else if (inBasicChannelSet && !waitingNextChanSetComm && isChannelSetCommunicationList(result))
      {        
        debugFlags(result, DEBUG_FINEST);
        assert checkChannelName(result) : "not valid channel name transformation in smart scanner at basic channel set expression";
      }
      else 

      //if this is a colon decl, convert the DECORWORD to a DECLWORD
      if (isColonDecl()) 
      {
        result.sym = Sym.DECLWORD;
      }

    }
    else if (result.sym == Sym.SCH ||

             result.sym == Sym.GENSCH) 
    {
        inBoxName = true;
        
    }

    // if starting a basic process, mark it true and not def. action yet
    // unless the CIRCBEGIN is followed by a CIRCSPOT, in which case we 
    // have single environment process with just the main action
    // ex: \begin{circus} \circprocess P \circdef \circbegin \circspot \Skip \circend \end{circus}
    else if (result.sym == Sym.CIRCBEGIN)
    { 
       // reset the flags accordingly
       inCircusBasicProcess = true;
       inCircusActionEnv = false;
       inActionDef = false;
       waitingPrefixThen = false;
              
       // lookahead to see if we find a CIRCSPOT after NL
       LinkedList < Symbol > tokens = new LinkedList < Symbol >();
       Symbol currsym = dumb_.pop();
       tokens.addLast(currsym);

       //skip any newlines
       while (currsym.sym == Sym.NL) {
         currsym = dumb_.pop();
         tokens.addLast(currsym);
       }

       // check if is a main action definition or not
       inActionDef = currsym.sym == Sym.CIRCSPOT;

       // push the tokens back without changing any DECORWORD.
       pushList(tokens, false);  

       debugFlags(result, DEBUG_FINEST);
    }
    // if finishing a basic process, mark it fakse and not def. action yet
    else if (result.sym == Sym.CIRCEND)
    {
       inCircusBasicProcess = false;
       inCircusActionEnv = false;
       inActionDef = false;       
       waitingPrefixThen = false;
       debugFlags(result, DEBUG_FINEST);
    }
    // if defining an action and within a basic process, mark def. action true
    // CIRCDEF might appear outside basic process - ex. other processes definitions.
    else if (result.sym == Sym.CIRCDEF && inCircusBasicProcess)
    {       
       inActionDef = true;
       waitingPrefixThen = false;
       debugFlags(result, DEBUG_FINEST);
    }
    // if terminating some definition within a basic process, then 
    // mark def. action false. This only happens when we have circusaction 
    // latex environments, as within a single circus latex environment, paragraphs
    // are terminated/separated by new lines!
    else if (result.sym == Sym.END && inCircusBasicProcess)
    {       
       inCircusActionEnv = false;
       inActionDef = false;
       waitingPrefixThen = false;
       debugFlags(result, DEBUG_FINEST);
    }
    // if within a multiple LaTeX environment basic process definition, 
    // switch the inCircusActionEnv true, and inActionDef is false.
    // Also, to cope with the case of main action within CIRCUSACTION
    // we need to lookahead for a CIRCSPOT - in which case inActionDef is true.    
    else if (result.sym == Sym.CIRCUSACTION && inCircusBasicProcess)
    {
      inCircusActionEnv = true;
      waitingPrefixThen = false;
      
      LinkedList < Symbol > tokens = new LinkedList < Symbol >();
      Symbol currsym = dumb_.pop();
      tokens.addLast(currsym);
      
      //skip any newlines
      while (currsym.sym == Sym.NL) {
        currsym = dumb_.pop();
        tokens.addLast(currsym);
      }
      
      // check if is a main action definition or not
      inActionDef = currsym.sym == Sym.CIRCSPOT;
      
      // push the tokens back without changing any DECORWORD.
      pushList(tokens, false);  
      
      debugFlags(result, DEBUG_FINEST);
    }    
    // When we find a PREFIXTHEN, reset its waiting flag. 
    // This is important to avoid problems when having chained prefixes.
    // ex:  c \then d \then A
    else if (result.sym == Sym.PREFIXTHEN) //&& inCircusBasicProcess
    {
      waitingPrefixThen = false;
      debugFlags(result, DEBUG_FINE);
    }
    // When declaring a basic channel set expression, change inner elements
    // DECORWORDs accordingly to CHANNELNAME and FIELDNAME.
    else if (result.sym == Sym.LCIRCCHANSET)
    {
      inBasicChannelSet = true;
      waitingNextChanSetComm = false;
      commBracketDepth = 0;
      debugFlags(result, DEBUG_FINEST);
    }  
    // Although the processing of DECORWORDs after a LCIRCCHANSET may reset
    // the inBasicChannelSet flag, we still need to cope with the empty case
    // ex: \circchannelset\ C0 == \lchanset \rchanset 
    // Also this is important to get out of the BasicChannelSet enumeration case.
    else if (result.sym == Sym.RCIRCCHANSET)
    {
      inBasicChannelSet = false;
      waitingNextChanSetComm = false;
      debugFlags(result, DEBUG_FINEST);
      
      // TODO: raise ScanException here if commBracketDepth different from 0?
      assert commBracketDepth == 0 : "scanning error wtihin communication predicate - unbalanced parenthesis";
    }
    // When we find a COMMA, reset its waiting flag if outside PRED brackets. 
    // This is important to avoid problems when having chained communication enumerations in channel sets.
    // ex:  \lchanset c?x?y \prefixcolon (x > y), d?a \rchanset
    else if (inBasicChannelSet && waitingNextChanSetComm && commBracketDepth == 0 && result.sym == Sym.COMMA)
    {
      waitingNextChanSetComm = false;
      debugFlags(result, DEBUG_FINEST);    	
    }
    // this caters for both c.(x?).y, and c?x \prefixcolon (...),
    // we need to update them globally as well as within when we need to check for channel set expressions
    // this is to enable appropriate scanning of the updated stream resulting from lookahead scanning 
    else if (inBasicChannelSet && waitingNextChanSetComm && result.sym == Sym.LPAREN)
    {
      commBracketDepth++;	
    }
    else if (inBasicChannelSet && waitingNextChanSetComm && result.sym == Sym.RPAREN)
    {
      commBracketDepth--;	
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
  

  private void debug(String s, int level)
  {
    if (logDebugInfo() && debugLevel >= level)
    {       
      System.out.println(s + "; dialect = " + dialect_.toString() + " in SmartScanner."); 
    }
    logFormatted(s);
  }
  
  private void debugFlags(Symbol symbol, int level)
  {
    Throwable t = new Throwable();
    debug(symbol, 1, level); // show who called debugFlags
    debug("\tBASIC_PROCESS_DEF = " + (inCircusBasicProcess ? "T" :"F") + 
                 "; ACTION_DEF = " + (inActionDef ? "T" : "F") +
          "; CIRCUS_ACTION_ENV = " + (inCircusActionEnv ? "T" : "F") +
          "; WAITING_PREFIXTHEN = " + (waitingPrefixThen ? "T" : "F") +
          "; FIELD_COUNT = " + fieldCount +
          "; STROKE_COUNT = " + strokeCount +
          "; IN_BASIC_CHANSET = " + (inBasicChannelSet ? "T" : "F") +
          "; WAITING_NEXT_CS_COMM = " + (waitingNextChanSetComm ? "T" : "F") +
          "; COM_BRACKET_DEPTH = " + commBracketDepth +
          " at line " + t.getStackTrace()[1].getLineNumber() + " of " + t.getStackTrace()[1].getMethodName(),
          level);
  }
  
  private void debug(Symbol symbol, int level)
  { 
    debug(symbol, 1, level); // show who called debugFlags
  }
  
  private void debug(Symbol symbol, int throwableOffset, int level)
  {
    Throwable t = new Throwable();
    StackTraceElement[] steArray = t.getStackTrace();
    StackTraceElement ste = steArray.length > (throwableOffset+1) ? steArray[throwableOffset + 1] : steArray[steArray.length-1];
    debug("\tSymbol " + symbol.sym + ": " + get_sym_info(symbol) + "[L" + symbol.left + ";R"+ symbol.right + "] - " + 
        (symbol.value != null ? symbol.value.getClass() : "null value") +
        " at line " + ste.getLineNumber() + " of " + ste.getMethodName(), level);
        //+  (symbol.value instanceof Decorword ? "\n\t" + ((Decorword)symbol.value).getLocation() : ""));
  }  
  
  private void debug(List < Symbol > tokens, int level)
  {
    debug("***DEBUG-TOKENS-LIST-BEGIN", level);
    for (int i = tokens.size() - 1; i >= 0; i--) 
    {      
      debug(tokens.get(i), 1, level);
    }
    debug("***DEBUG-TOKENS-LIST-END", level);
  }
  
  /**
   * Channel name symbols cannot have strokes on its Decorword value.
   */
  private boolean checkChannelName(Symbol symbol)
  {
    return ((symbol.sym == Sym.CHANNELNAME || symbol.sym == Sym.CHANNELERROR) && 
          (symbol.value instanceof Decorword) && 
          ((Decorword)symbol.value).getStrokes().isEmpty());
  } 
  
  /**  
   * Create symbol out of the given stroke. Accept NextStroke and NumStroke
   * for simplicity - the parser will raise the problem later.
   */
  private Symbol toToken(Stroke stroke, int left, int right) throws ScanException
  {
    if (stroke instanceof InStroke) {      
      return new Symbol(Sym.CHANNELIN, left, right);
    }
    else if (stroke instanceof OutStroke) {
      return new Symbol(Sym.CHANNELOUT, left, right);
    }
    else if (stroke instanceof NextStroke) {
      return new Symbol(Sym.NEXTSTROKE, left, right);
    }
    else if (stroke instanceof NumStroke) {
      NumStroke numStroke = (NumStroke) stroke;
      return new Symbol(Sym.NUMSTROKE, left, right, numStroke.getDigit().getValue());
    }
    else {
      throw new ScanException(dialect_, "Unexpected stroke ", new LocInfoImpl(getDialect(), sourceName_, left, right));
    }
  }
  
  // depending on the case, we need to push symbols onto different places.
  //interface TokenPusher { void push(Symbol symbol); }
  
  /**
   * Given a Decorword (from a Symbol), and a list of symbols, update the list of
   * symbols with any strokes from the Decorword. That is done backwards since the
   * tokens list is actually a stack. We also update the location information for
   * the converted strokes so that they correspond to the right position in the 
   * original input stream. The result Decorword should be used to update the 
   * value of the calling symbol.
   */
  private void processDecorwordStrokes(/*TokenPusher tp*/ LinkedList<Symbol> tokens, Symbol symbol)
  { 
    assert (symbol.value instanceof Decorword) : "not Decorword symbol in processDecorwordStrokes(";
    
    // if there are strokes to add to the list of tokens
    Decorword dw = ((Decorword)symbol.value);
    ZStrokeList zsl = dw.getStrokes();
    
    if (!zsl.isEmpty())
    {
      // get the decorword "word-part" size to offset the symbol column position        
      int dwSize = dw.getWord().length() + zsl.size() - 1;

      Symbol sym;
      
      // adding last-to-first stroke at the front of the tokens list thus 
      // reconstructing the list of tokens properly - also offset the symbol.right
      ListIterator<Stroke> li = zsl.listIterator(zsl.size());
      while (li.hasPrevious())        
      {          
        Stroke s = li.previous();
        sym = toToken(s, symbol.left, symbol.right+dwSize);
        //tp.push(sym);
        tokens.addFirst(sym);
        
        // count flatten strokes 
        strokeCount ++;

        // remove the offset size of each stroke.
        dwSize--;
      }
      zsl = null;

      // update the decorword accordingly - no need for location update
      symbol.value = new Decorword(dw.getWord(), dw.getLocation());
    }    
  }
  
  /**
   * Pushes the list of symbols collected during the check for communication token stream
   * back into the main lexer. Depending on the value of isChannelName_, it changes decorwords
   * to properly represent the target symbol to FIELDNAME.
   *
   */
  private void pushCommPatternList(List < Symbol > tokens, boolean isFrontSymbolChannelName)
  {
    // if within gen actuals - or renaming of expressions for that matter, just don't 
    // change any name within the L/RSQUARE tokens.
    int withinGenActuals = 0;
    int withinParenExpr  = 0;
    
    boolean changed = false;
    
    Symbol symbol = null;
    Symbol previousSymbolOnStack = null;
    
    for (int i = tokens.size() - 1; i >= 0; i--) 
    {
      symbol = tokens.get(i);
      
      // check whether to consider changing DECORWORDs or not.
      if (isFrontSymbolChannelName)
      {
        // check whether to ignore DECORWORDs within (possibly nested) "[...]"
        withinGenActuals = symbol.sym == Sym.RSQUARE ? withinGenActuals + 1 :
                           symbol.sym == Sym.LSQUARE ? withinGenActuals - 1 :
                           withinGenActuals;
        
        // check whether to change DOTs within (possibly nested) "(...)" 
        // parenthesised expressions. This is useful to differentiate Z 
        // binding/tuple selection expressions within a communication.
        withinParenExpr  = symbol.sym == Sym.RPAREN ? withinParenExpr + 1 :
                           symbol.sym == Sym.LPAREN ? withinParenExpr - 1 :
                           withinParenExpr;
                
        debug("PUSH-COMM-SYM: P" + withinParenExpr + " G" + withinGenActuals + 
              " F" + fieldCount + " S" + strokeCount + "  " + 
              get_sym_info(symbol), DEBUG_FINE);
        
        // change symbols only when not within brackets 
        if (withinGenActuals == 0 && withinParenExpr == 0)
        {
          // if it is a DECORWORRD, change it to a FIELDNAME
          // and process any strokes found
          if (symbol.sym == Sym.DECORWORD)
          {
            changed = true;
            // count field change
            fieldCount++;            
            symbol.sym = Sym.FIELDNAME;            
            
            // process (flatten) field name strokes into the token stack
            LinkedList < Symbol > strokes = new LinkedList< Symbol >();
            processDecorwordStrokes(strokes, symbol);

            // add the field's strokes to the dumb_ TokenStack.
            Symbol strokeSymbol;            
            for (int j = strokes.size() - 1; j >= 0; j--) 
            {          
              strokeSymbol = strokes.get(j);
              dumb_.push(strokeSymbol);
            }            
          }
          // if a DOT (output/input) field, change it accordingly and count strokes
          else if (symbol.sym == Sym.DOT)
          {
            changed = true;
            strokeCount++;
            symbol.sym = Sym.CHANNELDOT;
          }
          else if (symbol.sym == Sym.INSTROKE)
          {
            changed = true;
            strokeCount++;
            symbol.sym = Sym.CHANNELIN;
          }
          else if (symbol.sym == Sym.OUTSTROKE)
          {
            changed = true;
            strokeCount++;
            symbol.sym = Sym.CHANNELOUT;
          }
          // if closing the last parenthesis of a field expression,
          // or if the field expression is a number (special case 
          // of expression where parenthesis are not mandatory),
          // then consider the whole expression as a fieldCount
          //
          // ex: c.0 \then \Skip 
          else if (symbol.sym == Sym.LPAREN || 
                   symbol.sym == Sym.NUMERAL)
          {
            fieldCount++;
          }
          // on the other hand, in the case of a prefix colon, we need
          // to ignore one such counts, since it also requires a parenthesis
          // ex: c?x \prefixcolon (x > 0)!(x+1) \then \Skip
          //     counts as one field: x; (x+1) 
          //     shouldn't be counted: (x>0)
          else if (symbol.sym == Sym.PREFIXCOLON && 
                   previousSymbolOnStack != null &&
                   previousSymbolOnStack.sym == Sym.LPAREN)
          {
            fieldCount--;
          }              
        }        
      }
      dumb_.push(symbol);
      
      // save previous symbol so that we know how to count field names.
      // that is, if previousSymbolOnStack is LPAREN, and we hit PREFIXCOLON
      // the parenthesis was for a predicate on an input prefix
      previousSymbolOnStack = symbol;
    } 
    if (changed)
    {
      debug(tokens, DEBUG_FINE);
    }
  }
  
  /**
   * Looks ahead from the current DECORDWORD symbol until a Circus symbol is found. 
   * If the symbol found is a symbol to lookahead, it returns true and the symbol 
   * at the point of call should be changed to CHANNELNAME. Any strokes within the
   * channel name are added to the token stack. Also, any field names for the communication,
   * as well as theirs strokes, are also added to the stack. DECORWORDs within
   * expressions and generic actuals are left unchanged.    
   *
   *@param symbol the symbol to start looking ahead
   *@param tokens the list of tokens modified by this method. They ought to be pushed back onto the token stack
   *@param symbolsToLookahead the list of Sym values to match the lookahead once the search stops. For
   *                          prefixing action that is PREFIXCOLON or PREFIXTHEN, whereas for basic channel
   *                          set expression that is RCIRCCHANSET.
   */  
  private boolean isChannelSetCommunicationList(Symbol frontSymbol)
  	throws Exception
  {
  	// look ahead on the right place only please
  	assert inBasicChannelSet : "invalid point to lookahead for channel name";
  	
  	CommunicationScanner commS = new CommunicationScanner() 
  	{
      public boolean lookaheadTest(Symbol currentSymbol)
      {
    		// while not having all brackets closed, then carry on looking ahead 
    		// (i.e commas are part of the predicate of PREFIXCOLON)   
    	    // and also, in case of a quite messy spec that reaches EOF too early
    	    //
    	    // PREFIXCOLON is a special case: we still need to look forward
        return currentSymbol.sym == Sym.PREFIXCOLON ||
	         commBracketDepth != 0 || 
             (currentSymbol.sym != Sym.COMMA && 
              currentSymbol.sym != Sym.RCIRCCHANSET &&
              currentSymbol.sym != Sym.EOF);
      }
      
      public boolean scanningResult(Symbol currentSymbol)
   	  {
          // if found a COMMA or RCIRCCHANSET symbol
          // the bracket deptdh is meant to be established by the while loop, here we double check on it
      	return commBracketDepth == 0 && 
       		   (currentSymbol.sym == Sym.COMMA || currentSymbol.sym == Sym.RCIRCCHANSET);
   	  }
      
      public void doWithinLookaheadLoop(Symbol currentSymbol)
      {
        if (currentSymbol.sym == Sym.LPAREN) 
        {
          commBracketDepth++;
        }
        else if (currentSymbol.sym == Sym.RPAREN)
        {
          commBracketDepth--;   	   
        }
      }
    };
    
    // process communication with specific communication scanner
    boolean result = processSymbolWithCommunicationScanner(commS, frontSymbol);
    
    // Waiting for next communication? important to tell normal lexing to not
    // consider DECORWORDs between \prefixcolon PRED and comma , separating channelset comms
    //
    // This is important to prevent smart lexing within the communication predicate part
    // for DECORWORDS (i.e. they never need to become CHANNELNAMEs. 
    waitingNextChanSetComm = result;
    
    // return the result to the next_token() call    
    return result;
  }
  
  /**
   * Looks ahead from the current DECORDWORD symbol at the right place
   * (i.e., within a basic process after an action declaration) until
   * a Circus symbol is found (see isCommunicationPattern). That is, it
   * checks if the symbol forms a communication pattern for PREFIXTHEN
   * as the symbol to lookahead. As PREFIXTHEN could be chained, another
   * flag is also set here in order to avoid changing genuine DECORWORDs
   * within the communication pattern (i.e. those on expressions) to
   * FIELDNAME or CHANNELNAME.
   */
  private boolean isChannelName(Symbol frontSymbol) 
    throws Exception 
  {
    // look ahead on the right place only, please
    assert (inCircusBasicProcess && inActionDef) : "invalid point to lookahead for channel name";

    CommunicationScanner commS = new CommunicationScanner() 
  	{
      public boolean lookaheadTest(Symbol currentSymbol)
      {
      	// while finding non Circus symbols, then carry on looking ahead    
        // and also, in case of a quite messy spec that reaches EOF too early
        //
        // PREFIXCOLON is a special case: we still need to look forward
        return (currentSymbol.sym == Sym.PREFIXCOLON ||
            (!CIRCUS_SYMBOL_NAMES_ONLY.contains(get_sym_name(currentSymbol)) && 
            		currentSymbol.sym != Sym.EOF));
      }
      
      public boolean scanningResult(Symbol currentSymbol)
   	  {
      	// if we see PREFIXTHEN, we know we are finished and this is a communication 
        return (currentSymbol.sym == Sym.PREFIXTHEN);
   	  }
      
      public void doWithinLookaheadLoop(Symbol currentSymbol)
      {
        // nothing special todo
      }
    };
    
    // process communication with specific communication scanner
    boolean result = processSymbolWithCommunicationScanner(commS, frontSymbol);
    
    // if is a channel name, we are waiting for the PREFIXTHEN symbol.
    // until that point, there is no need to search for DECORWORDs
    // to be changed as CHANNELNAME. This may happen within fields with
    // complex expressions. (e.g., read?i!(ring~i) \then Skip, DECORWORD("ring")).
    //
    // This is important to prevent smart lexing within the communication predicate part
    // for DECORWORDS (i.e. they never need to become CHANNELNAMEs. 
    waitingPrefixThen = result;

    // return the result to the next_token() call    
    return result;
  } 
  
  interface CommunicationScanner 
  {
  	public boolean lookaheadTest(Symbol currentSymbol);
  	public boolean scanningResult(Symbol currentSymbol);
  	public void doWithinLookaheadLoop(Symbol currentSymbol);
  }
  
  /**
   * Given a frontSymbol (DECORWORD), and final symbol being processed update token stream to reflect the
   * tokens the parser expects for communication patterns (e.g. CHANNELNAME for DECORWORD, DOTFIELD for DOT, etc).
   * For prefixing, the expected final symbol is PREFIXTHEN, whereas for channel set expressions that is either COMMA or RCHANSET.
   * 
   * The communication scanner delegates the test for the result of this function to see whether the final symbol
   * represents an appropriate decisions to whether or not to change the token stream. 
   * 
   * @param commS communication scanner used to delegate final symbol test.
   * @param frontSymbol initial DECORWORD to consider in smart scanning of communication
   * @return whether or not to change the underlying token stream within the communication pattern.
   */
  private boolean processSymbolWithCommunicationScanner(CommunicationScanner commS, Symbol frontSymbol)
  	throws Exception
  {
  	assert frontSymbol.sym == Sym.DECORWORD : "front symbol in communication must be DECORWORD";

  	debug(frontSymbol, DEBUG_FINER);
  	
  	LinkedList<Symbol> tokens = new LinkedList<Symbol>();
    Symbol currentSymbol;

    // get the next symbol handling any new lines involved
    do {
      currentSymbol = dumb_.pop();
      tokens.addLast(currentSymbol);
      commS.doWithinLookaheadLoop(currentSymbol);
    }
    while (currentSymbol.sym == Sym.NL);
    
    // perform lookahead test and processing depending on the 
    // delegated communication scanner needs
    while (commS.lookaheadTest(currentSymbol))
    {    
      currentSymbol = dumb_.pop();
      tokens.addLast(currentSymbol);
      commS.doWithinLookaheadLoop(currentSymbol);
    }
    
    // assume this processing will not change the token stream
    boolean result = false;
    
    // process communication tokens by changing their inner tokens to communication tokens
    // e.g. DECORDWORD to CHANNELNAME/ERROR, DOT to DOTFIELD, IN to FIELDIN etc.
    Symbol finalSymbol = currentSymbol;
    
 	  // Checks if in EOF and if so raise error; otherwise see what to do with the lookahead buffer
    if (finalSymbol.sym == Sym.EOF)
    {
      checkEOF(finalSymbol, tokens);
    }
    else
    {
      // i.e. Sym.XXXX = expectedCurrSymbolAs = currsym.sym
      result = commS.scanningResult(finalSymbol); //(finalSymbol.sym == expectedFinalSymbolSymCode); 
    }
    
    // update the calling symbol to become a channel name, adding any
    // strokes to the list of tokens. Repeated strokes (i.e. c??x) will 
    // be caught by the parser as an error.
    if (result)
    {       
      // update the symbol to be channel name and add any possible strokes
    	frontSymbol.sym   = Sym.CHANNELNAME;      
      processDecorwordStrokes(tokens, frontSymbol);      
      // debug(symbol, DEBUG_FINER); - debugFlags called outside will do it.
    }
      
    // push the tokens back, changing them to FIELDNAME according to the result.
    pushCommPatternList(tokens, result);
    
    if (result && fieldCount != strokeCount)
    {      
    	frontSymbol.sym = Sym.CHANNELERROR;            
      Decorword channelName = (Decorword)frontSymbol.value;
      
      // if this is a Decorword, add the information to it
      if (channelName != null)
      {
        channelName.setExtraInfo(new Pair<Integer, Integer>(strokeCount, fieldCount));
      }      
      debug(frontSymbol, DEBUG_FEW);
      //warningManager_ = new WarningManager(SmartScanner);
      //warningManager_.warn(WarningMessage.WRONG_NUMBER_FIELD_STROKES, 
      //  channelName, strokeCount, fieldCount, get_sym_name(symbol), 
      //  channelName != null ? channelName.getLocation() : 
      //    new LocInfoImpl(null, symbol.left, symbol.right));      
    }    
    
    // reset  stroke and field name count
    strokeCount = 0;
    fieldCount = 0;
    
    return result;
  }
  
  // NOTE: BasicChannelSetExpr does not accept fields (for now) - so easy = list of names!
//    //skip any formal parameters
//    if (currsym.sym == Sym.LSQUARE) 
//    {
//      while (currsym.sym != Sym.RSQUARE) {
//        currsym = dumb_.pop();
//        tokens.addLast(currsym);
//      }
//      currsym = dumb_.pop();
//      tokens.addLast(currsym);
//    }
//        
//    if (!isCommPattern)
//    {
//      net.sourceforge.czt.util.CztLogger.getLogger(SmartScanner.class).warning("Could not find closing basic channel set bracket token (RCIRCCHANSET) at \n\t" + ((Decorword)symbol.value).getLocation());
//    }
  
  
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
