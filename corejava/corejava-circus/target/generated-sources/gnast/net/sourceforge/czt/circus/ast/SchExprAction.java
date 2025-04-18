
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
          An action given as a schema expression.
          In this way, schema expressions are included into the <code>CircusAction</code> subtree
          in a similar way as schema expressions are included in the declaration and predicate subtrees.
        </p>
        <p>
          As one might use a schema expression for defining a Z schema (i.e. S and T), this class contains an 
          Z <code>Expr</code> instead of <code>SchExpr</code>. The parser is responsible to rule out other 
          possibilities and ensure that only well formed schema expressions are allowed.
          From the CZT these came through <code>SchExpr</code>, <code>SchExpr2</code>, <code>DecorExpr</code>,
          <code>NegExpr</code>, and <code>RenameExpr</code>. (TODO: Check this carefully).
        </p>        
        <p>
          For the process state, this action or a the corresponding Z paragraph must contain a <code>CircusStateAnn</code>.
        </p>
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface SchExprAction extends 		 		net.sourceforge.czt.circus.ast.CircusAction
 			{

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
}
