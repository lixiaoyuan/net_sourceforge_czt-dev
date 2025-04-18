
/*
  Copyright (C) 2004, 2005, 2006 Petra Malik
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

package net.sourceforge.czt.parser.zpatt;

import java.io.IOException;
import java.util.Properties;
import net.sourceforge.czt.parser.util.CztLexerImpl;
import net.sourceforge.czt.parser.util.LocToken;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.session.Dialect;

/**
 * <p>
 *   The high-level latex to unicode converter for Z .
 * </p>
 * <p>
 *   This class is responsible for translating
 *   Z 
 *   specifications written in the latex mark-up language into unicode.
 *   It provides tokens whose values
 *   are unicode strings and which contain line and column number
 *   information from the original latex file or stream.  This makes it
 *   possible to use this class as a standalone translater and, via
 *   {@link net.sourceforge.czt.parser.util.CztReader},
 *   as the input to the unicode scanner and therefore
 *   for the Z parser.
 * </p>
 */
public class LatexToUnicode
  extends CztLexerImpl
{
  private final LatexMarkupParser lexer_;

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.ZPATT
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  /**
   * Ignores the markup information contained in source and treats the
   * content of source as LaTeX.
   */
  public LatexToUnicode(Source source, SectionInfo sectInfo, Properties properties)
    throws IOException
  {
    super(properties);
    Latex2Unicode l2u = new Latex2Unicode(source, sectInfo, properties);
    lexer_ = new LatexMarkupParser(l2u, sectInfo);
    assert dialect_ != null;
    if (!dialect_.equals(sectInfo.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in LatexScanner. " + dialect_.toString() +
    		" expected, but section manager dialect " + sectInfo.getDialect() + " found.");     
    if (!dialect_.equals(lexer_.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in LatexScanner. " + dialect_.toString() +
    		" expected, but Latex2Unicode lexer dialect " + lexer_.getDialect() + " found.");     
  }
  
  @Override
  public Dialect getDialect()
  {
    assert dialect_.equals(lexer_.getDialect()) : 
    		"dialect = " + dialect_ + "; lexer.dialect = " + lexer_.getDialect();
  	return lexer_.getDialect();
  }

  public LocToken next()
    throws IOException
  {
    LocToken result = lexer_.next();
    logToken(result);
    return result;
  }

  @Override
  public Source getSource()
  {
    return lexer_.getSource();
  }
}
