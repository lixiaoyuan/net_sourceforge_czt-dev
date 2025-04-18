
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
 * A class type.
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface ClassType extends 		 						net.sourceforge.czt.z.ast.Type2
						 			{

  /**
   * Returns the Classes element.
   *
   * @return the Classes element.
   */
  net.sourceforge.czt.oz.ast.ClassRefList getClasses();


  /**
   * Sets the Classes element.
   *
   * @param classes   the Classes element.
   * @see #getClasses
   */
  void setClasses(net.sourceforge.czt.oz.ast.ClassRefList classes);

  /**
   * Returns the State element.
   *
   * @return the State element.
   */
  net.sourceforge.czt.z.ast.Signature getState();


  /**
   * Sets the State element.
   *
   * @param state   the State element.
   * @see #getState
   */
  void setState(net.sourceforge.czt.z.ast.Signature state);

  /**
   * <p>Returns the Attribute elements.</p>
   * <p>To add or remove elements, use the methods provided by
   * the List interface (that's why there is no need for a setter
   * method).</p>
   *
   * @return a list of NameTypePair elements.
   */
  net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.z.ast.NameTypePair> getAttribute();

  /**
   * <p>Returns the Operation elements.</p>
   * <p>To add or remove elements, use the methods provided by
   * the List interface (that's why there is no need for a setter
   * method).</p>
   *
   * @return a list of NameSignaturePair elements.
   */
  net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.oz.ast.NameSignaturePair> getOperation();
}
