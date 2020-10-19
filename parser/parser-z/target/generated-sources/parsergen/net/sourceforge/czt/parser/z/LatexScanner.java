
/*
  Copyright 2003, 2004, 2005 Tim Miller
  Copyright 2003, 2004, 2005, 2006 Petra Malik
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
import java.util.Properties;

import java_cup.runtime.Symbol;

import net.sourceforge.czt.parser.util.CztScannerImpl;
import net.sourceforge.czt.parser.util.CztReader;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.Source;

import net.sourceforge.czt.session.Dialect;

/**
 * A latex scanner.  This is a convenience class that
 * feeds the result of the latex to unicode converter
 * into the unicode scanner.
 */
/**
 * <p>A latex scanner for  Z specifications.
 * It scans  Z specifications
 * written in the LaTeX marku-up language as defined by the
 * international Z standard.  Note that the token values, like e.g.
 * the value of the token <code>DECORWORD</code>,
 * are unicode strings, not LaTeX strings.</p>
 *
 * <p>This is a convenience class that feeds the output of the
 * latex to unicode converter ({@link LatexToUnicode})
 * into the unicode scanner ({@link UnicodeScanner}).  The {@link CztReader}
 * in between the two is responsible for preserving
 * line and column number information.</p>
 */
public class LatexScanner
  extends CztScannerImpl
{
  private final LatexToUnicode latexLexer_;
  private final UnicodeScanner unicodeLexer_;

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.Z
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  /**
   * Creates a new scanner.
   *
   * @param reader the Reader to read input from.
   */
  public LatexScanner(Source source,
                  SectionInfo sectInfo,
                  Properties properties)
    throws IOException
  {
    latexLexer_ = new LatexToUnicode(source, sectInfo, properties);
    CztReader cztReader = new CztReader(latexLexer_);
    unicodeLexer_ = new UnicodeScanner(cztReader, properties);
    assert dialect_ != null;
    if (!dialect_.equals(sectInfo.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in LatexScanner. " + dialect_.toString() +
    		" expected, but section manager dialect " + sectInfo.getDialect() + " found."); 
  }
  
  @Override
  public Dialect getDialect()
  {
    assert dialect_.equals(latexLexer_.getDialect()) &&
    	   dialect_.equals(unicodeLexer_.getDialect()) : 
    	   "dialect_ " + dialect_ + "; latexLexer.dialect = " + 
    	   latexLexer_.getDialect() + "; unicodeLexer.dialect = " + 
    	   unicodeLexer_.getDialect();
  	return latexLexer_.getDialect();
  }
  
  @Override
  public Symbol next_token()
    throws Exception
  {
    Symbol symbol = unicodeLexer_.next_token();
    return symbol;
  }
  
  @Override
  protected Class<?> getSymbolClass()
  {
    return Sym.class;
  }
  
}
