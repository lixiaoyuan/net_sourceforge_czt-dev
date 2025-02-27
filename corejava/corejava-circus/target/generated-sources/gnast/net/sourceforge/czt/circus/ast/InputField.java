
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

package net.sourceforge.czt.circus.ast;

																																																				

/**
 * <p>
          An input field that is part of a communication.
          It represents grammar rules <b>?N</b> and <b>?N: Pred</b>.
        </p>
        <p>
          The parse must ensure that the declared name <b>N</b> does not have strokes.
        </p>
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface InputField extends 		 		net.sourceforge.czt.circus.ast.Field
 			{

  /**
   * Returns the VariableName element.
   *
   * @return the VariableName element.
   */
  net.sourceforge.czt.z.ast.Name getVariableName();


  /**
   * Sets the VariableName element.
   *
   * @param variableName   the VariableName element.
   * @see #getVariableName
   */
  void setVariableName(net.sourceforge.czt.z.ast.Name variableName);

  /**
   * Returns the Restriction element.
   *
   * @return the Restriction element.
   */
  net.sourceforge.czt.z.ast.Pred getRestriction();


  /**
   * Sets the Restriction element.
   *
   * @param restriction   the Restriction element.
   * @see #getRestriction
   */
  void setRestriction(net.sourceforge.czt.z.ast.Pred restriction);

  /**
   * This is a convenience method.
   * It returns the ZName if VariableName is an instance of
	   * ZName and throws an UnsupportedAstClassException otherwise.
   */
  net.sourceforge.czt.z.ast.ZName getVariableZName();
}
