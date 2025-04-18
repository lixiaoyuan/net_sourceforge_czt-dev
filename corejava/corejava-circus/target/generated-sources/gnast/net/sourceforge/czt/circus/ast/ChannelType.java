
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
          A channel type is like a generic type, where the type can be <code>null</code> for synchronisation channels.
          The <code>OptionalType</code> is the resolved type after instantiation via generic actuals or type inference.
        </p>
        <p>
          It contains an underlying base type, which corresponds to the maximal type of the declared channel type.
          One should also look at the CommunicationType, which contains the actual signature of the communication
          related to the channel type. For instance, in a declaration like "channel [X] c: X \cross X", the type
          will be ChannelType(GenericType(List(ZName("X")), ProdType(List(X, X)))).
        </p>
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface ChannelType extends 		 		net.sourceforge.czt.circus.ast.CircusType
 			{

  /**
   * Returns the Type element.
   *
   * @return the Type element.
   */
  net.sourceforge.czt.z.ast.Type2 getType();


  /**
   * Sets the Type element.
   *
   * @param type   the Type element.
   * @see #getType
   */
  void setType(net.sourceforge.czt.z.ast.Type2 type);
}
