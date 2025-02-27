
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
          An abstract parallel action definition;
          it includes the name set partitions of the state.
        </p>
        <p>
          As empty name disjoint sets are often used in parallel actions, syntactic sugar for these cases
          is provided in the grammar rules for interleaving, parallel composition, and alphabetised parallel 
          composition, so that we do not need explicit AST classes. 
        </p>
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface ParAction extends 		 		net.sourceforge.czt.circus.ast.Action2
 			{

  /**
   * <p>Returns the NameSet elements.</p>
   * <p>To add or remove elements, use the methods provided by
   * the List interface (that's why there is no need for a setter
   * method).</p>
   *
   * @return a list of NameSet elements.
   */
  net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.circus.ast.NameSet> getNameSet();

  NameSet getLeftNameSet();
  void setLeftNameSet(NameSet alpha);
  NameSet getRightNameSet();
  void setRightNameSet(NameSet alpha);
}
