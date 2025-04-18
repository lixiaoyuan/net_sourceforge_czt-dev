
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

package net.sourceforge.czt.zeves.ast;

																																																				

/**
 * Label to theorems and axiomatic predicates
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface ZEvesLabel extends   		net.sourceforge.czt.base.ast.Term
			{

  /**
   * Returns the Name element.
   *
   * @return the Name element.
   */
  net.sourceforge.czt.z.ast.Name getName();


  /**
   * Sets the Name element.
   *
   * @param name   the Name element.
   * @see #getName
   */
  void setName(net.sourceforge.czt.z.ast.Name name);

  /**
   * Returns the LabelAbility element.
   *
   * @return the LabelAbility element.
   */
  LabelAbility getLabelAbility();


  /**
   * Sets the LabelAbility element.
   *
   * @param labelAbility   the LabelAbility element.
   * @see #getLabelAbility
   */
  void setLabelAbility(LabelAbility labelAbility);

  /**
   * Returns the LabelUsage element.
   *
   * @return the LabelUsage element.
   */
  LabelUsage getLabelUsage();


  /**
   * Sets the LabelUsage element.
   *
   * @param labelUsage   the LabelUsage element.
   * @see #getLabelUsage
   */
  void setLabelUsage(LabelUsage labelUsage);

  /**
   * This is a convenience method.
   * It returns the ZName if Name is an instance of
	   * ZName and throws an UnsupportedAstClassException otherwise.
   */
  net.sourceforge.czt.z.ast.ZName getZName();}
