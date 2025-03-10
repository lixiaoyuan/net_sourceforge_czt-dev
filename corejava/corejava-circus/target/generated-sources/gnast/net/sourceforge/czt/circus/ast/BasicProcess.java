
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
          A process definition for an explicit process declaration.
          It is formed by a (possibly empty) set of process paragraphs,
          an optional process state given as a schema and a (mandatory) 
          main action definition.
        </p>
        <p>
          It represents the grammar rule <b>begin</b> <code>PParagraph*</code> <b>state</b> <code>Schema-Exp PParagraph*</code>
          <b>@</b> <code>Action</code> <b>end</b>.
        </p>
        <p>
          For the process state, the corresponding action (as a SchExprAction) or Z paragraph (as a Schema or HorizontalDef) 
          must contain a <code>CircusStateAnn</code> annotation.
        </p>
 *
 * @author GnAST version 1.6-cdh
 */ 
 



public interface BasicProcess extends 		 		net.sourceforge.czt.circus.ast.CircusProcess
 			{

  /**
   * Returns the ParaList element.
   *
   * @return the ParaList element.
   */
  net.sourceforge.czt.z.ast.ParaList getParaList();


  /**
   * Sets the ParaList element.
   *
   * @param paraList   the ParaList element.
   * @see #getParaList
   */
  void setParaList(net.sourceforge.czt.z.ast.ParaList paraList);
  /**
   * This is a convenience method. It returns the state paragraph within the getZParaList().
   * That is, the first paragraph with a CircusStateAnn. A state paragraph is always a 
   * horizonal AxPara. When declared implicitly a default name is given with the prefix
   * CircusUtils.DEFAULT_PROCESS_STATE_NAME.
   */
  net.sourceforge.czt.z.ast.AxPara getStatePara();
  
  /**
   * This method checks whether the state paragraph is valid. That is, if getStatePara() != null
   * and that getZParaList() contains exactly one state element. The parser always creates a 
   * default state for basic process declared without state. So, after parsing, this method 
   * should always be true. It may be false when the parser is still creating the underlying 
   * structures, or when created manually by the user.
   */
  boolean isStateValid();
  
  /**
   * Returns true iff getStatePara() != null and the result is the default state paragraph
   * as created by the parser. The default state paragraph is empty (i.e., horizontal box 
   * named CircusUtils.DEFAULT_PROCESS_STATE_NAME. with empty declarations and true predicate). 
   * @return
   */
  boolean isDefaultState();
  
  /**
   * This is a convenience method. It returns the main action within the getZParaList().
   * The method checks that only one such paragraph exists, that it has a 
   * OnTheFlyAnn in it, and that its name is CircusUtils.DEFAULT_MAIN_ACTION_NAME.
   * All these invariants are guaranteed by both the parser and typechecker. 
   */  
  net.sourceforge.czt.circus.ast.CircusAction getMainAction(); 
  
  boolean isMainActionValid();
  
  /**
   * This is a convenience method. It returns a unmodifiable list of declared paragraphs 
   * within the getZParaList(); they may be either Circus or Z paragraphs. 
   * That is, all paragraphs from getZParaList() that have not been declared on-the-fly.
   * All these invariants are guaranteed by both the parser and typechecker. 
   */   
  java.util.List<? extends net.sourceforge.czt.z.ast.Para> getLocalPara();    
    
  /**
   * This is a convenience method. It returns a unmodifiable list of on-the-fly paragraphs 
   * within the getZParaList(); they can only be ActionPara, since there are no Z on-the-fly paragraphs.
   * That is, all paragraphs from getZParaList() that have been declared on-the-fly.
   * All elements in the resulting list contain a OnTheFlyAnn.
   * All these invariants are guaranteed by both the parser and typechecker. 
   */   
  java.util.List<? extends net.sourceforge.czt.z.ast.Para> getOnTheFlyPara();    

  /**
   * This is a convenience method. It represents the non-null (possibly empty)
   * list of paragraphs for the basic process. It contains both Z and Circus paragraphs.
   * It may throw a UnsupportedAstClassException is #getParaList() is a ParaJoker.
   * This method forms a partition between getOnTheFlyPara() and getLocalPara().
   * All these invariants are guaranteed by both the parser and typechecker.    
   */
  net.sourceforge.czt.z.ast.ZParaList getZParaList();

  /**
   * This is a convenience method. It represents the non-null name of the 
   * (possibly implicitly) declared process state name. 
   */
  net.sourceforge.czt.z.ast.Name getStateParaName();

  /**
   * This is a convenience method. It casts getStateParaName() into a ZName.
   */
  net.sourceforge.czt.z.ast.ZName getStateParaZName();  

}
