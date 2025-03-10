
/******************************************************************************
DO NOT EDIT THIS FILE!  THIS FILE WAS GENERATED BY GNAST
FROM THE TEMPLATE FILE AstInterface.vm.
ANY MODIFICATIONS TO THIS FILE WILL BE LOST UPON REGENERATION.

-------------------------------------------------------------------------------

Copyright 2003, 2004, 2005, 2006, 2007 Mark Utting
This file is part of the czt project.

The czt project contains free software; you can redistribute it and/or modify
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
******************************************************************************/

package net.sourceforge.czt.oz.ast;

																																																				

/**
 * An operation text.
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface OpText extends   		net.sourceforge.czt.base.ast.Term
			{

  /**
   * Returns the DeltaList element.
   *
   * @return the DeltaList element.
   */
  net.sourceforge.czt.oz.ast.DeltaList getDeltaList();


  /**
   * Sets the DeltaList element.
   *
   * @param deltaList   the DeltaList element.
   * @see #getDeltaList
   */
  void setDeltaList(net.sourceforge.czt.oz.ast.DeltaList deltaList);

  /**
   * Returns the SchText element.
   *
   * @return the SchText element.
   */
  net.sourceforge.czt.z.ast.SchText getSchText();


  /**
   * Sets the SchText element.
   *
   * @param schText   the SchText element.
   * @see #getSchText
   */
  void setSchText(net.sourceforge.czt.z.ast.SchText schText);
  /**
   * This is a convenience method.
   * It returns the ZSchText if SchText is an instance of
   * ZSchText and throws an UnsupportedAstClassException otherwise.
   */
  net.sourceforge.czt.z.ast.ZSchText getZSchText();

}
