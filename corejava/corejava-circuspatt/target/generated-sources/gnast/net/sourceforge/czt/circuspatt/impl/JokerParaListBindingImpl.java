
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
 * {@link net.sourceforge.czt.circuspatt.ast.JokerParaListBinding}.
 *
 * @author GnAST version 1.6-cdh
 */
public class JokerParaListBindingImpl
extends 		 									net.sourceforge.czt.zpatt.impl.BindingImpl
						 	  implements net.sourceforge.czt.circuspatt.ast.JokerParaListBinding
{





  /**
   * static instance count for JokerParaListBindingImpl
   */
   private static long instanceCount_ = 0;
   
  /**
   * public attribute determining whether to log to the 
   * standard output information about who is creating 
   * this instance of JokerParaListBindingImpl (e.g., ic >= sl)
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
  protected JokerParaListBindingImpl()
  {
    this(null);
  }

  protected JokerParaListBindingImpl(net.sourceforge.czt.base.impl.BaseFactory factory)
  {
    super(factory);
    countInstance();
  }
  
  private static synchronized void countInstance()
  {
	  instanceCount_++;
  }
  
  /**
   * Compares the specified object with this JokerParaListBindingImpl
   * for equality.  Returns true if and only if the specified object is
   * also a(n) JokerParaListBindingImpl and all the getter methods except getAnns
   * return equal objects.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass()) && super.equals(obj)) {
      	// if foreach on meta-model (.vm) file is empty, this won't be used.
      	//@SuppressWarnings("unused")
        //JokerParaListBindingImpl object = ((JokerParaListBindingImpl) obj);
        if (jokerParaList_ != null) {
          if (!jokerParaList_.equals(((JokerParaListBindingImpl) obj).jokerParaList_)) {
            return false;
          }
        }
        else {
          if (((JokerParaListBindingImpl) obj).jokerParaList_ != null) {
            return false;
          }
        }
        if (paraList_ != null) {
          if (!paraList_.equals(((JokerParaListBindingImpl) obj).paraList_)) {
            return false;
          }
        }
        else {
          if (((JokerParaListBindingImpl) obj).paraList_ != null) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the hash code value for this JokerParaListBindingImpl.
   */
  @Override
  public int hashCode()
  {
    //final int constant = 31;

    int hashCode = super.hashCode();
    hashCode += "JokerParaListBindingImpl".hashCode();
    if (jokerParaList_ != null) {
      hashCode += 31 * jokerParaList_.hashCode();
    }
    if (paraList_ != null) {
      hashCode += 31 * paraList_.hashCode();
    }
    return hashCode;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public <R> R accept(net.sourceforge.czt.util.Visitor<R> v)
  {
    if (v instanceof net.sourceforge.czt.circuspatt.visitor.JokerParaListBindingVisitor) {
      net.sourceforge.czt.circuspatt.visitor.JokerParaListBindingVisitor<R> visitor = 
      	  (net.sourceforge.czt.circuspatt.visitor.JokerParaListBindingVisitor<R>) v;
      return visitor.visitJokerParaListBinding(this);
    }
    return super.accept(v);
  }

  /**
   * Returns a new object of this class.
   */
  public JokerParaListBindingImpl create(Object[] args)
  {
    JokerParaListBindingImpl zedObject = null;
    try {
	  	  			  	net.sourceforge.czt.circuspatt.ast.JokerParaList jokerParaList = (
						net.sourceforge.czt.circuspatt.ast.JokerParaList) args[0];
	  	  	  			  	net.sourceforge.czt.z.ast.ParaList paraList = (
						net.sourceforge.czt.z.ast.ParaList) args[1];
	        zedObject = new JokerParaListBindingImpl(getFactory());
      zedObject.setJokerParaList(jokerParaList);
      zedObject.setParaList(paraList);
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
    Object[] erg = { getJokerParaList(), getParaList() };
    return erg;
  }
  
  /** Only concrete classes get the instance count method visible */
  // JokerCommunication in circuspatt extends CommunicationImpl which is not abstract? weird...
  public static /*final*/ long instanceCount()
  {
    return instanceCount_;
  }


  private
            		net.sourceforge.czt.circuspatt.ast.JokerParaList
  jokerParaList_;

  public 
		net.sourceforge.czt.circuspatt.ast.JokerParaList
  getJokerParaList()
  {
    return jokerParaList_;
  }

  public void setJokerParaList(
		net.sourceforge.czt.circuspatt.ast.JokerParaList
	jokerParaList)
  {
                          jokerParaList_ = jokerParaList;
    }


  private
            		net.sourceforge.czt.z.ast.ParaList
  paraList_;

  public 
		net.sourceforge.czt.z.ast.ParaList
  getParaList()
  {
    return paraList_;
  }

  public void setParaList(
		net.sourceforge.czt.z.ast.ParaList
	paraList)
  {
                          paraList_ = paraList;
    }

  public void reset()
  {
    setParaList(null);
  }
}
