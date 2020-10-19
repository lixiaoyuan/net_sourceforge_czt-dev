

package net.sourceforge.czt.parser.zeves;

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

 */
class SmartScanner extends CztScannerImpl
{
  //indicates whether the previous token is a named box token
  //i.e. SCH, GENSCH, CLASS, OPSCH, ZPROOF
  private boolean inBoxName = false;

  
  
  private TokenStack dumb_;

  
  /**
   * Tests whether we are within a Z proof environment or not.
   */
  private boolean inZProof_ = false;

  private boolean seenLet_ = false;
  private boolean seenInstantiate_ = false;
  
  private final String sourceName_;
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.ZEVES
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

    }
    else if (result.sym == Sym.SCH ||

             result.sym == Sym.ZPROOF ||
             //result.sym == Sym.ZPROOFSECTION ||

             result.sym == Sym.GENSCH) 
    {
        inBoxName = true;
        
        inZProof_ = result.sym == Sym.ZPROOF;
        seenLet_ = false;
        seenInstantiate_ = false;
        
    }

    else if (result.sym == Sym.END)
    {
      inZProof_ = false;
      seenLet_ = false;
      seenInstantiate_ = false;
    }
    else if (inZProof_)
    {
      // change SEMICOLON to ZPROOFCOMMANDSEP in zproof environments
      if (result.sym == Sym.SEMICOLON)
      {
        if (isProofCmdSep())
        {
          result.sym = Sym.ZPROOFCOMMANDSEP;
          // if reached ";" then couldn't have seen instantiate command
          seenLet_ = false;
          seenInstantiate_ = false;
        }
      }
      // if seen instantiate command tag it
      else if (result.sym == Sym.INSTANTIATE)
      {
        seenInstantiate_ = true;
      }
      // if seen instantiate (E.g., within ZPROOF instantiate or ";" instantiate)
      // then the first DEFEQUAL is definitely the QNTINSTANTIATION, although future
      // == might be from (unlikely) LET-EXPRESSIONs: "instantiate x == \LET v == y @ v+1
      //
      // instantiate x == y captures "==" as DEFEQUAL; needs to be QNTINST
      // to avoid reduce conflicts on refName from invoke and instantiate?
      else if (seenInstantiate_)
      {
        if (result.sym == Sym.LET)
        {
          // instantiate x == \LET v == d @ d+1, y == z;
          seenLet_ = true;
        }
        else if (!seenLet_ && result.sym == Sym.DEFEQUAL)
        {
          result.sym = Sym.QNTINSTANTIATION;
          // only change the seenInst/Let flags after ";", otherwise we would
          // mistakenly miss second "==" of "instantiate x == y, z == y";
        }
        else if (seenLet_ && result.sym == Sym.SPOT)
        {
          // after @ in LET, DEFEQUAL is part of instantiate
          seenLet_ = false;
        }
      }
    }
    
    return result;
  }


  /**
   * Current symbol is a semicolon, then it looks ahead to see whether it is a
   * proof command separator or part of a Z term. It returns trus if a head proof
   * word is found ahead with out any semicolon in between
   * @return true if the current symbol is a semicolon preceeded by a head proof word, false otherwise
   * @throws Exception
   */
  private boolean isProofCmdSep() throws Exception
  {
    // look ahead on the right place only, please
    assert inZProof_  : "invalid point to lookahead for proof cmd sep - not in a zproof environment";

    // assume it is not proof cmd se
    boolean result = false;

    // consume any possible NL between commands; In LaTeX stream they are not present; In Unicode they might
    LinkedList < Symbol > tokens = new LinkedList < Symbol >();
    Symbol currsym;
    do {
      currsym = dumb_.pop();
      tokens.addLast(currsym);
    }
    while (currsym.sym == Sym.NL);

    // in case of an early EOF hit
    result = checkEOF(currsym, tokens);

    // this is a proof cmd sep if the next symbol is either END or a head proof word
    result =
      (//currsym.sym == Sym.PROOFWORD &&
        ZEvesSymMap.HEAD_PROOF_WORDS_ONLY.contains(get_sym_name(currsym)))
      ||
      (currsym.sym == Sym.END);

    // put the symbol back onto the stack
    for (int i = tokens.size() - 1; i >= 0; i--) {
      Symbol symbol = tokens.get(i);
      dumb_.push(symbol);
    }

    // return whether to flip symbols or not
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
