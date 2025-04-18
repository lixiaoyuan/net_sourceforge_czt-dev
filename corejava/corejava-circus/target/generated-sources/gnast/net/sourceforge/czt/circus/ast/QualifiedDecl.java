
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
 * This is like <code>VarDecl</code>, but it includes qualifier attributes for each <code>Name</code>.
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface QualifiedDecl extends 		 						net.sourceforge.czt.z.ast.Decl
						 			{

  /**
   * Returns the NameList element.
   *
   * @return the NameList element.
   */
  net.sourceforge.czt.z.ast.NameList getNameList();


  /**
   * This is a convenience method.
   * It returns a ZNameList if #getNameList
   * returns an instance of ZNameList
   * and throws an UnsupportedAstClassException otherwise.
   */
  net.sourceforge.czt.z.ast.ZNameList getZNameList();

  /**
   * Sets the NameList element.
   *
   * @param nameList   the NameList element.
   * @see #getNameList
   */
  void setNameList(net.sourceforge.czt.z.ast.NameList nameList);

  /**
   * Returns the Expr element.
   *
   * @return the Expr element.
   */
  net.sourceforge.czt.z.ast.Expr getExpr();


  /**
   * Sets the Expr element.
   *
   * @param expr   the Expr element.
   * @see #getExpr
   */
  void setExpr(net.sourceforge.czt.z.ast.Expr expr);

  /**
   * Returns the ParamQualifier element.
   *
   * @return the ParamQualifier element.
   */
  ParamQualifier getParamQualifier();


  /**
   * Sets the ParamQualifier element.
   *
   * @param paramQualifier   the ParamQualifier element.
   * @see #getParamQualifier
   */
  void setParamQualifier(ParamQualifier paramQualifier);
}
