
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

package net.sourceforge.czt.circuspatt.ast;

																																																				

/**
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface JokerActionBinding extends 		 									net.sourceforge.czt.zpatt.ast.Binding
						 			{

  /**
   * Returns the JokerAction element.
   *
   * @return the JokerAction element.
   */
  net.sourceforge.czt.circuspatt.ast.JokerAction getJokerAction();


  /**
   * Sets the JokerAction element.
   *
   * @param jokerAction   the JokerAction element.
   * @see #getJokerAction
   */
  void setJokerAction(net.sourceforge.czt.circuspatt.ast.JokerAction jokerAction);

  /**
   * Returns the CircusAction element.
   *
   * @return the CircusAction element.
   */
  net.sourceforge.czt.circus.ast.CircusAction getCircusAction();


  /**
   * Sets the CircusAction element.
   *
   * @param circusAction   the CircusAction element.
   * @see #getCircusAction
   */
  void setCircusAction(net.sourceforge.czt.circus.ast.CircusAction circusAction);
}
