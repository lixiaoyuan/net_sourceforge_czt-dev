
/******************************************************************************
DO NOT EDIT THIS FILE!  THIS FILE WAS GENERATED BY GNAST
FROM THE TEMPLATE FILE AstClass.vm.
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

package net.sourceforge.czt.circuspatt.impl;

																																																				

/**
 * An implementation of the interface
 * {@link net.sourceforge.czt.circuspatt.ast.JokerAction}.
 *
 * @author GnAST version 1.6-cdh
 */
public class JokerActionImpl
extends 		 												net.sourceforge.czt.circus.impl.CircusActionImpl
			 	  implements net.sourceforge.czt.circuspatt.ast.JokerAction
{





  /**
   * static instance count for JokerActionImpl
   */
   private static long instanceCount_ = 0;
   
  /**
   * public attribute determining whether to log to the 
   * standard output information about who is creating 
   * this instance of JokerActionImpl (e.g., ic >= sl)
   */
  //private static final long startLoggingFrom_ = Long.MAX_VALUE;
   
    // cannot be final because JokerCommunicationImpl extends CommunicationImpl
  public /*final*/ static boolean countingFinaliser() { return false; }
  public /*final*/ static long instancesFinalised() 
  { 
    throw new UnsupportedOperationException("GnAST AST finalisers have not been set"); 
  }
  
  /**
   * The default constructor.
   *
   * Do not use it explicitly, unless you are extending this class.
   * If you want to create an instance of this class, please use the
   * {@link net.sourceforge.czt.circuspatt.ast.CircusPatternFactory object factory}.
   */
  protected JokerActionImpl()
  {
    this(null);
  }

  protected JokerActionImpl(net.sourceforge.czt.base.impl.BaseFactory factory)
  {
    super(factory);
    countInstance();
  }
  
  private static synchronized void countInstance()
  {
	  instanceCount_++;
  }
  
  /**
   * Compares the specified object with this JokerActionImpl
   * for equality.  Returns true if and only if the specified object is
   * also a(n) JokerActionImpl and all the getter methods except getAnns
   * return equal objects.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass()) && super.equals(obj)) {
      	// if foreach on meta-model (.vm) file is empty, this won't be used.
      	//@SuppressWarnings("unused")
        //JokerActionImpl object = ((JokerActionImpl) obj);
        if (name_ != null) {
          if (!name_.equals(((JokerActionImpl) obj).name_)) {
            return false;
          }
        }
        else {
          if (((JokerActionImpl) obj).name_ != null) {
            return false;
          }
        }
        if (id_ != null) {
          if (!id_.equals(((JokerActionImpl) obj).id_)) {
            return false;
          }
        }
        else {
          if (((JokerActionImpl) obj).id_ != null) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the hash code value for this JokerActionImpl.
   */
  @Override
  public int hashCode()
  {
    //final int constant = 31;

    int hashCode = super.hashCode();
    hashCode += "JokerActionImpl".hashCode();
    if (name_ != null) {
      hashCode += 31 * name_.hashCode();
    }
    if (id_ != null) {
      hashCode += 31 * id_.hashCode();
    }
    return hashCode;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public <R> R accept(net.sourceforge.czt.util.Visitor<R> v)
  {
    if (v instanceof net.sourceforge.czt.circuspatt.visitor.JokerActionVisitor) {
      net.sourceforge.czt.circuspatt.visitor.JokerActionVisitor<R> visitor = 
      	  (net.sourceforge.czt.circuspatt.visitor.JokerActionVisitor<R>) v;
      return visitor.visitJokerAction(this);
    }
    return super.accept(v);
  }

  /**
   * Returns a new object of this class.
   */
  public JokerActionImpl create(Object[] args)
  {
    JokerActionImpl zedObject = null;
    try {
	  	  								  	String name = (
													String) args[0];
	  	  	  								  	String id = (
													String) args[1];
	        zedObject = new JokerActionImpl(getFactory());
      zedObject.setName(name);
      zedObject.setId(id);
    }
    catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(e);
    }
    catch (ClassCastException e) {
      throw new IllegalArgumentException(e);
    }
    return zedObject;
  }

  public Object[] getChildren()
  {
    Object[] erg = { getName(), getId() };
    return erg;
  }
  
  /** Only concrete classes get the instance count method visible */
  // JokerCommunication in circuspatt extends CommunicationImpl which is not abstract? weird...
  public static /*final*/ long instanceCount()
  {
    return instanceCount_;
  }


  private
            					String
  name_;

  public 
					String
  getName()
  {
    return name_;
  }

  public void setName(
					String
	name)
  {
                          name_ = name;
    }


  private
            					String
  id_;

  public 
					String
  getId()
  {
    return id_;
  }

  public void setId(
					String
	id)
  {
                          id_ = id;
    }
}
