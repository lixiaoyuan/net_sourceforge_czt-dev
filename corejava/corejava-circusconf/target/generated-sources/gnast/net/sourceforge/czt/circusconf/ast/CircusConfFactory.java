
/******************************************************************************
DO NOT EDIT THIS FILE!  THIS FILE WAS GENERATED BY GNAST
FROM THE TEMPLATE FILE CoreFactory.vm.
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

package net.sourceforge.czt.circusconf.ast;


/**
 * <p>The object factory for the AST.
 * This interface contains factory methods
 * for each concrete Z term.</p>
 *
 * <p>This object factory allows the programmer to programatically
 * construct new instances of concrete Z terms.
 * There is a factory method that does not require arguments
 * (called default factory method)
 * and a factory method where all the children (except annotations)
 * of that particular Z term can be provided.</p>
 *
 * @author GnAST version 1.6-cdh
 */
public interface CircusConfFactory
  extends net.sourceforge.czt.circuspatt.ast.CircusPatternFactory
{
  /**
   * Creates an instance of {@link ConfidentialityAction}.
   *
   * @return the new instance of ConfidentialityAction.
   */
  net.sourceforge.czt.circusconf.ast.ConfidentialityAction createConfidentialityAction();

  /**
   * Creates an instance of {@link ConfidentialityAction} with the given children.
   *
   * @return the new instance of ConfidentialityAction.
   */
  net.sourceforge.czt.circusconf.ast.ConfidentialityAction createConfidentialityAction( net.sourceforge.czt.circus.ast.CircusAction  circusAction);

  /**
   * Creates an instance of {@link ConfidentialityProcess}.
   *
   * @return the new instance of ConfidentialityProcess.
   */
  net.sourceforge.czt.circusconf.ast.ConfidentialityProcess createConfidentialityProcess();

  /**
   * Creates an instance of {@link ConfidentialityProcess} with the given children.
   *
   * @return the new instance of ConfidentialityProcess.
   */
  net.sourceforge.czt.circusconf.ast.ConfidentialityProcess createConfidentialityProcess( net.sourceforge.czt.circus.ast.CircusProcess  circusProcess);

}
