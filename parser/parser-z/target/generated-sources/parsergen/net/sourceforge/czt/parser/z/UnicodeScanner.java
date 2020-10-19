
/*
  Copyright (C) 2003, 2004, 2006 Petra Malik
  This file is part of the czt project.

  The czt project contains free software;
  you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  The czt project is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with czt; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package net.sourceforge.czt.parser.z;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.CztReader;
import net.sourceforge.czt.parser.util.CztScannerImpl;
import net.sourceforge.czt.parser.util.DebugUtils;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.session.FileSource;
import net.sourceforge.czt.session.Markup;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.util.CztLogger;


/**
 * <p>A scanner for lexing
 * specifications in unicode format.</p>
 *
 * This is a convenience class that assembles the
 * {@link ContextFreeScanner}, {@link KeywordScanner}, and
 * {@link SmartScanner}; providing an implementation of chapters
 * 7.2 till 7.4.4 of the ISO Standard for Z.</p>
 *
 * <p>Note that the token stream provided by this scanner does not yet
 * have operator names resolved to operator tokens and still contains soft
 * newlines.  This is handled by the {@link OperatorScanner}, which is created
 * by the parser.</p>
 *
 * @author Petra Malik
 * @see net.sourceforge.czt.parser.z
 */
public class UnicodeScanner
  extends CztScannerImpl
{
  private final ContextFreeScanner contextFreeScanner_;
  private final KeywordScanner keywordScanner_;
  private final SmartScanner smartScanner_;

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private static final Dialect dialect_ = 
  						Dialect.Z
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;

  public UnicodeScanner(Source source, Properties properties)
    throws IOException
  {
    super(properties);
    contextFreeScanner_ = new ContextFreeScanner(source);
    keywordScanner_ = new KeywordScanner(contextFreeScanner_, properties);
    smartScanner_ = new SmartScanner(source.toString(), keywordScanner_);
  }

  public UnicodeScanner(CztReader in, Properties properties)
  {
    super(properties);
    contextFreeScanner_ = new ContextFreeScanner(in);
    keywordScanner_ = new KeywordScanner(contextFreeScanner_, properties);
    smartScanner_ = new SmartScanner("CztReader" + in.hashCode(), keywordScanner_);
  }
  
  @Override
  public Dialect getDialect()
  {
  	assert dialect_.equals(contextFreeScanner_.getDialect()) : 
  			"dialect = " + dialect_ + "; ctfScanner.dialect = " + 
  			contextFreeScanner_.getDialect();
  	return contextFreeScanner_.getDialect();
  }

  /**
   * Lexes a given file.
   */
  public static void main(String argv[])
  {
    try {
      Source source = new FileSource(argv[0]);
      source.setMarkup(Markup.UNICODE);
      source.setEncoding("UTF-8");
      UnicodeScanner scanner = new UnicodeScanner(source, new Properties());
      DebugUtils.scan(scanner, Sym.class);
    }
    catch (Exception e) {
      e.printStackTrace();
      CztLogger.getLogger(UnicodeScanner.class).severe(e.getClass().getName() + 
      		" thrown during UnicodeScanner debugging for dialect " + 
      		dialect_.toString() + " as : " + e.getMessage());
    }
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
    Symbol symbol = smartScanner_.next_token();
    logSymbol(symbol);
    return symbol;
  }

  public void reset(Reader in)
  {
    try {
      contextFreeScanner_.yyreset(in);
    } catch (Throwable e) 
    {
    	CztLogger.getLogger(UnicodeScanner.class).severe("Exception " + e.getClass().getName() + 
    		" thrown during UnicodeScanner reset for " + 
	    	dialect_.toString() + " with message " + e.getMessage());
    }
  }
}
