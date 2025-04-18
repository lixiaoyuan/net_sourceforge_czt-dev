
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

package net.sourceforge.czt.circustime.ast;

																																																				

/**
 * Timed Range action. This AST encompases the three cases of wait operators,
        where the use of the AST determines which one is the case. Whenever N is null (or ???)
        then we have the first two cases (wait Expr; wait Expr .. Expr), whereas when N is not null
        we have (wait N: Expr .. Expr). In any case the upto expression only allows Arithmos type, so
        it's not quite needed anyway in so far as the AST is concerned.
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface WaitExprAction extends 		 		net.sourceforge.czt.circustime.ast.ActionTime1
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
 
}
