
package net.sourceforge.czt.parser.oz;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.CztScannerImpl;
import net.sourceforge.czt.parser.util.DebugUtils;
import net.sourceforge.czt.parser.util.Decorword;
import net.sourceforge.czt.parser.util.LocInfoImpl;
import net.sourceforge.czt.parser.util.LocInt;
import net.sourceforge.czt.parser.util.Token;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.util.CztException;
import net.sourceforge.czt.util.CztLogger;
import net.sourceforge.czt.z.ast.InStroke;
import net.sourceforge.czt.z.ast.LocAnn;
import net.sourceforge.czt.z.ast.NextStroke;
import net.sourceforge.czt.z.ast.NumStroke;
import net.sourceforge.czt.z.ast.OutStroke;
import net.sourceforge.czt.z.ast.Stroke;
import net.sourceforge.czt.z.ast.ZStrokeList;


/**
 * <p>This is a keyword lexer (part of context-sensitive lexis) for
 * Object Z
 * specifications in unicode format.</p>
 *
 * <p>According to the ISO Standard for Z, the lexis for Z specifications
 * consists of two phases: the context-free lexis and the context-sensitive
 * lexis.  This class is an implementation of sections 7.4.2 and 7.4.3
 * of the ISO Z standard, which describes the context-sensitive lexis.
 * </p>
 * 
 * <p>This class transforms a stream of tokens into another stream of
 * tokens.  The input is usually provided by the {@link ContextFreeScanner}.
 * Each DECORWORD token whose spelling is exactly that of a keyword is mapped
 * to the corresponding keyword token.  All other tokens are left unchanged.
 * </p>
 */
public class KeywordScanner
  extends CztScannerImpl
{
  private ContextFreeScanner scanner_;
  private static Map <String, Integer> keywords_ = createKeywordMap();
  private Stack<Symbol> tokens_ = new Stack<Symbol>();
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  static final Dialect dialect_ = 
  						Dialect.OZ
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
  
  public KeywordScanner(ContextFreeScanner scanner, Properties properties)
  {
    super(properties);
    if (scanner == null) throw new NullPointerException();
    scanner_ = scanner;
    assert dialect_ !=null;
    if (!dialect_.equals(scanner_.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in KeywordScanner. " + dialect_.toString() +
    		" expected, but ContextFreeScanner dialect " + scanner_.getDialect() + " found.");    
  }
  
  @Override
  public Dialect getDialect()
  {
  	assert scanner_.getDialect().isExtensionOf(dialect_) :
  		   "dialect_ = " + dialect_ + "; scanner.dialect = " + scanner_.getDialect(); 
	return scanner_.getDialect();
  }

  private static Map <String, Integer> createKeywordMap()
  {
    Map <String, Integer> result = new HashMap<String, Integer>();

    /* Adds all Z Standard keyword tokens */
    addKeywords(result, net.sourceforge.czt.parser.z.ZKeyword.values());


    /* Adds all Object-Z keyword tokens */
    addKeywords(result, net.sourceforge.czt.parser.oz.OzKeyword.values());

    return result;
  }
  
  private static void addKeywords(Map <String, Integer> result, Token[] keywords) {
    for (Token keyword : keywords) {
      final Integer symbol = SymMap.getMap().get(keyword.toString());
      if (symbol == null) {
        final String message = "Cannot map keyword " + keyword +
          " to a parser token of " + dialect_.toString() + " in KeywordScanner.";
        throw new CztException(message);
      }
      addKeyword(result, keyword.spelling(), symbol);
    }
  }

  private static Map<Object, String> symbolMap_ = DebugUtils.getFieldMap(Sym.class);

  /**
   * <p>
   * Maps the given keyword string with a specific <code>Symbol</code>
   * by including it into the <code>keyword_</code> map. 
   * </p>
   * <p>
   * If a previous mapping to same keyword already exists, a warning message is given.
   * It is useful while debugging the parsers/scanners to ensure that the Unicode
   * symbols used are indeed unique.
   * </p>
   */
  private static void addKeyword(Map <String, Integer> map,
                                 String keyword,
                                 int symbol) {
    Integer old = map.put(keyword, Integer.valueOf(symbol));        
    if (old != null) {
      final String logMessage = java.text.MessageFormat.format(
        "Invalid keyoword mapping [{0} -> {1}] for {3} in KeywordScanner. The keyword '{0}' has already been mapped to token {2}.",
        keyword, symbolMap_.get(symbol), symbolMap_.get(old), dialect_.toString());
      CztLogger.getLogger(KeywordScanner.class).severe(logMessage);
    }
  }

  private Symbol toToken(Stroke stroke, int left, int right)
  {
    if (stroke instanceof InStroke) {
      return new Symbol(Sym.INSTROKE, left, right);
    }
    else if (stroke instanceof OutStroke) {
      return new Symbol(Sym.OUTSTROKE, left, right);
    }
    else if (stroke instanceof NextStroke) {
      return new Symbol(Sym.NEXTSTROKE, left, right);
    }
    else if (stroke instanceof NumStroke) {
      NumStroke numStroke = (NumStroke) stroke;
      LocAnn locAnn = (LocAnn) stroke.getAnn(LocAnn.class);
      LocInt locInt = new LocInt(numStroke.getDigit().getValue(), new LocInfoImpl(getDialect(), locAnn));
      return new Symbol(Sym.NUMSTROKE, left, right, locInt);
    }
    else {
      throw new CztException("Unexpected stroke for " + getDialect().toString() + " in KeywordScanner.");
    }
  }

  public Symbol next_token()
    throws IOException
  {
    if (! tokens_.empty()) return tokens_.pop();
    Symbol symbol = scanner_.next_token();
    logSymbol(symbol);
    if (symbol.sym == Sym.DECORWORD 
    ) {
      Decorword decorword = (Decorword) symbol.value;
      if ("".equals(decorword.getWord())) {
        ZStrokeList strokes = decorword.getStrokes();
        assert strokes.size() > 0;
        for (int i = strokes.size() - 1; i > 0; i--) {
          tokens_.push(toToken(strokes.get(i), symbol.left, symbol.right));
        }
        return toToken(strokes.get(0), symbol.left, symbol.right);
      }
      String value = decorword.getName();
      Integer token = (Integer) keywords_.get(value);
      if (token != null) {
        logFormatted("{0}: decorword {1} -> {2} named as {3} for {4} dialect.",
            getClass().getName(), 
            value, 
            token, 
            getSymbolMap().get(token),
            getDialect().toString());
        return new Symbol(token.intValue(),
                          symbol.left, symbol.right,
                          decorword.getLocation());
      }
    }
    return symbol;
  }

  @Override
  protected Class<?> getSymbolClass()
  {
    return Sym.class;
  }
}
