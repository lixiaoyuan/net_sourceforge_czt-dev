

package net.sourceforge.czt.parser.circustime;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.CztScanner;
import net.sourceforge.czt.parser.util.CztScannerImpl;
import net.sourceforge.czt.parser.util.Decorword;
import net.sourceforge.czt.parser.util.LocString;
import net.sourceforge.czt.parser.util.OpTable;
import net.sourceforge.czt.parser.util.OperatorTokenType;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.util.CztException;
import net.sourceforge.czt.util.CztLogger;

/**
 * <p>This is an operator lexer
 * (part of context-sensitive lexis) for
 Circus 
 * specifications in unicode format.</p>
 *
 * <p>According to the ISO Standard for Z, the lexis for Z specifications
 * consists of two phases: the context-free lexis and the context-sensitive
 * lexis.  This class is an implementation of sections 7.4.4 and 7.5
 * of the ISO Z standard, which describes the context-sensitive lexis.
 * </p>
 * 
 * <p>This class transforms a stream of tokens into another stream of
 * tokens.  It translates words (DECORWORD or DECLWORD tokens)
 * into operator tokens like L, I, etc.
 * There are two cases in which a transformation to operator tokens
 * is not performed:
 *  <ul>
 *    <li>When parsing an operator template, so that an operator
 *      can be re-used (see section 8.3).</li>
 *    <li>When parsing a section header, so that section names
 *      can be operator names (see section 8.4).</li>
 *   </ul>
 * </p>
 *
 * <p>The transformation into operator tokens requires a lookup in an
 * operator table, which must be set appropriately before the lookup is
 * performed.  This is usually done by the parser who updates the operator
 * table while parsing.  This is not standard conforming since the operator
 * table gets updated when an operator template is parsed while the standard
 * requires that the scope of the associations between words and operator
 * tokens is the whole of the section in which the operator template appears
 * (not just from the operator template onwards).</p>
 */
public class OperatorScanner
  extends CztScannerImpl
{
  private final CztScanner scanner_;
  private OpTable table_;
  //private Symbol previous_ = null;
  private Symbol lookAhead_ = null;
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.CIRCUSTIME
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;

  /**
   * Indicates whether to perform a requested lookup.
   * There are two cases in which a lookup is not performed:
   *   1) When parsing an operator template, so that an operator
   *      can be re-used (see section 8.3)
   *   2) When parsing a section header, so that section names
   *      can be operator names (see section 8.4)
   */
  private boolean lookup_ = true;

  OperatorScanner(CztScanner scanner, Properties properties)
  {
    super(properties);
    if (scanner == null) throw new NullPointerException();
    scanner_ = scanner;
    assert dialect_ !=null;
    if (!dialect_.equals(scanner_.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in OperatorScanner. " + dialect_.toString() +
    		" expected, but CztScanner dialect " + scanner_.getDialect() + " found.");    
  }
  
  @Override
  public Dialect getDialect()
  {
    assert dialect_.equals(scanner_.getDialect()) : "dialect " + dialect_ + "; scanner.dialect = " + scanner_.getDialect();
  	return scanner_.getDialect();
  }

  public OpTable getOperatorTable()
  {
    return table_;
  }

  public void setOperatorTable(OpTable table)
  {
    table_ = table;
  }
  
  @Override
  public Symbol next_token()
    throws Exception
  {
    Symbol result = null;
    if (lookAhead_ == null) {
      result = localLookup(scanner_.next_token());
      lookAhead_ = scanner_.next_token();
    }
    else {
      result = localLookup(lookAhead_);
      lookAhead_ = scanner_.next_token();
    }
    assert lookAhead_ != null;
    // If a ZED token is followed by a SECTION, ignore the ZED
    // (this solves some shift/reduce problems)
    // and set lookup_ to false.
    if (result.sym == Sym.ZED) {
      while (lookAhead_.sym == Sym.NL) {
        lookAhead_ = scanner_.next_token();
      }
      if (lookAhead_.sym == Sym.SECTION) {
        lookAhead_.value = result.value;
        lookup_ = false;
        result = lookAhead_;
        lookAhead_ = scanner_.next_token();
      }
    }
    else if (result.sym == Sym.RELATION ||
             result.sym == Sym.FUNCTION ||
             result.sym == Sym.GENERIC) {
      lookup_ = false;
    }
    else if (lookup_ == false && result.sym == Sym.END) {
      lookup_ = true;
    }
    logSymbol(result);
    //previous_ = result;
    return result;
  }

  @Override
  protected Class<?> getSymbolClass()
  {
    return Sym.class;
  }


  /**
   * Lookup the value of this symbol
   */
  protected Symbol localLookup(Symbol symbol)
  {
    if (lookup_ == false) { 
      return symbol;
    }
    Symbol result = null;
    if (symbol.sym == Sym.DECORWORD ||

        symbol.sym == Sym.DECLWORD) {
      Decorword decorword = (Decorword) symbol.value;
      String name = decorword.getName();
      assert table_ != null;
      OperatorTokenType optype = table_.getTokenType(decorword);
      int type = -1;
      if (optype != null) {
        Field[] fields = Sym.class.getFields();
        for (int i = 0; i < fields.length; i++) {
          Field field = fields[i];
          try {
            if (Modifier.isStatic(field.getModifiers())) {
              if (optype.toString().equals(field.getName())) {
                type = ((Integer) field.get(null)).intValue();
              }
            }
          }
          catch (IllegalAccessException e) {
          	CztLogger.getLogger(OperatorScanner.class).severe(e.getMessage() + 
          		" for " + dialect_.toString() + " in OperatorScanner.");
            throw new CztException(e);
          }
        }
        assert type != -1;
      }
      LocString locName = new LocString(name, decorword.getLocation());
      result = (type == -1) ? symbol : new Symbol(type, 
                                                  symbol.left, 
                                                  symbol.right,
                                                  locName);
    }
    else {
      result = symbol;
    }
    return result;
  }
}
