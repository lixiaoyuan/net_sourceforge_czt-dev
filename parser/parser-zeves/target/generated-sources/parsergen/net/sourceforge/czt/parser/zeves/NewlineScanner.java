

package net.sourceforge.czt.parser.zeves;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.CztScanner;
import net.sourceforge.czt.parser.util.CztScannerImpl;
import net.sourceforge.czt.parser.util.NewlineCategory;
import net.sourceforge.czt.parser.util.Token;
import net.sourceforge.czt.parser.util.TokenStack;
import net.sourceforge.czt.session.Dialect;



/**
 * <p>This is a NL lexer
 * (part of context-sensitive lexis) for
 * specifications in unicode format.</p>
 */
public class NewlineScanner
  extends CztScannerImpl
{
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  static final Dialect dialect_ = 
  						Dialect.ZEVES
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  private static Map<Integer,NewlineCategory> map_ = createMap();

  private static Map<Integer,NewlineCategory> createMap()
  {
    Map<Integer,NewlineCategory> result =
      new HashMap<Integer,NewlineCategory>();
    Map<String,NewlineCategory> map =
      new HashMap<String,NewlineCategory>();
    map.put("_APPLICATION", null);
    map.put("_RENAME", null);
    map.put("EXPR", null);
    map.put("PRED", null);
    map.put("PARA", null);
    map.put("EOF", null);
    map.put("error", null);
    map.put("DECLWORD", net.sourceforge.czt.parser.z.ZToken.DECORWORD.getNewlineCategory());
    insertInto(map, net.sourceforge.czt.parser.z.ZToken.values());
    insertInto(map, net.sourceforge.czt.parser.z.ZKeyword.values());
    insertInto(map, net.sourceforge.czt.parser.z.ZOpToken.values());

    insertInto(map, ZEvesProofToken.values());
    insertInto(map, ZEvesProofKeyword.values());
    map.put("PROOFWORD", NewlineCategory.BOTH);
    map.put("THMNAME", NewlineCategory.BOTH);

    for (String s : SymMap.getMap().keySet()) {
      if (! map.containsKey(s)) {
        final String msg = "No new line category for token or keyword " + s + " was found for " + 
        	dialect_.toString() + " in NewlineScanner";
        System.err.println(msg);
        throw new IllegalStateException(msg);
      }
      NewlineCategory category = map.get(s);
      result.put(SymMap.getMap().get(s), category);
    }
    return result;
  }

  private static void insertInto(Map<String,NewlineCategory> map,
                                 Token[] tokens)
  {
    for (Token t : tokens) {
      map.put(t.getName(), t.getNewlineCategory());
    }
  }

  /**
   * The token returned via the last call to #next_token,
   * or <code>null</code> if #next_token has not yet been called.
   */
  private Symbol symbol_ = null;

  /**
   * The token stream from which NL tokens are to be removed.
   * Should never be <code>null</code>.
   */
  private final TokenStack tokens_;

  NewlineScanner(CztScanner scanner, Properties properties)
  {
    super(properties);
    tokens_ = new TokenStack(scanner);
    assert dialect_ != null;
    if (!dialect_.equals(scanner.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in NewlineScanner. " + dialect_.toString() +
    		" expected, but CztScanner dialect " + scanner.getDialect() + " found.");     
  }
  
  @Override
  public Dialect getDialect()
  {
  	assert dialect_.equals(tokens_.getDialect()) : "dialect = " + dialect_ + "; tokens.dialect = " + tokens_.getDialect();
  	return tokens_.getDialect();
  }

  @Override
  public Symbol next_token()
    throws Exception
  {
    symbol_ = next();
    return symbol_;
  }

  private Symbol next()
    throws Exception
  {
    Symbol symbol = tokens_.pop();
    logSymbol(symbol);
    if (symbol.sym == Sym.NL &&
        (previousAllowsNewline() ||
         nextAllowsNewline())) {
      return next();
    }
    return symbol;
  }

  private boolean previousAllowsNewline()
  {
    if (symbol_ == null) return true;
    final int sym = symbol_.sym;
    final NewlineCategory category = map_.get(sym);
    return NewlineCategory.BOTH == category ||
      NewlineCategory.AFTER == category;
  }

  private boolean nextAllowsNewline()
    throws Exception
  {
    Symbol next = tokens_.pop();
    // consecutive NLs are treated the same as a single NL
    while (next.sym == Sym.NL) {
      next = tokens_.pop();
    }
    final int sym = next.sym;
    final NewlineCategory category = map_.get(sym);
    tokens_.push(next);
    return NewlineCategory.BOTH == category ||
      NewlineCategory.BEFORE == category;
  }
  
  @Override
  protected Class<?> getSymbolClass()
  {
    return Sym.class;
  }

}
