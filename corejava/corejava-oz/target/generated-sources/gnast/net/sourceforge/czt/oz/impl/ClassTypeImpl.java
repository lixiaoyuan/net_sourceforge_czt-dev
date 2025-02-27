
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

package net.sourceforge.czt.oz.impl;

																																																				

/**
 * An implementation of the interface
 * {@link net.sourceforge.czt.oz.ast.ClassType}.
 *
 * @author GnAST version 1.6-cdh
 */
public abstract class ClassTypeImpl
extends 		 						net.sourceforge.czt.z.impl.Type2Impl
						 	  implements net.sourceforge.czt.oz.ast.ClassType
{






  /**
   * The default constructor.
   *
   * Do not use it explicitly, unless you are extending this class.
   * If you want to create an instance of this class, please use the
   * {@link net.sourceforge.czt.oz.ast.OzFactory object factory}.
   */
  protected ClassTypeImpl()
  {
    this(null);
  }

  protected ClassTypeImpl(net.sourceforge.czt.base.impl.BaseFactory factory)
  {
    super(factory);
  }
  
  
  /**
   * Compares the specified object with this ClassTypeImpl
   * for equality.  Returns true if and only if the specified object is
   * also a(n) ClassTypeImpl and all the getter methods except getAnns
   * return equal objects.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass()) && super.equals(obj)) {
      	// if foreach on meta-model (.vm) file is empty, this won't be used.
      	//@SuppressWarnings("unused")
        //ClassTypeImpl object = ((ClassTypeImpl) obj);
        if (classes_ != null) {
          if (!classes_.equals(((ClassTypeImpl) obj).classes_)) {
            return false;
          }
        }
        else {
          if (((ClassTypeImpl) obj).classes_ != null) {
            return false;
          }
        }
        if (state_ != null) {
          if (!state_.equals(((ClassTypeImpl) obj).state_)) {
            return false;
          }
        }
        else {
          if (((ClassTypeImpl) obj).state_ != null) {
            return false;
          }
        }
        if (attribute_ != null) {
          if (!attribute_.equals(((ClassTypeImpl) obj).attribute_)) {
            return false;
          }
        }
        else {
          if (((ClassTypeImpl) obj).attribute_ != null) {
            return false;
          }
        }
        if (operation_ != null) {
          if (!operation_.equals(((ClassTypeImpl) obj).operation_)) {
            return false;
          }
        }
        else {
          if (((ClassTypeImpl) obj).operation_ != null) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the hash code value for this ClassTypeImpl.
   */
  @Override
  public int hashCode()
  {
    //final int constant = 31;

    int hashCode = super.hashCode();
    hashCode += "ClassTypeImpl".hashCode();
    if (classes_ != null) {
      hashCode += 31 * classes_.hashCode();
    }
    if (state_ != null) {
      hashCode += 31 * state_.hashCode();
    }
    if (attribute_ != null) {
      hashCode += 31 * attribute_.hashCode();
    }
    if (operation_ != null) {
      hashCode += 31 * operation_.hashCode();
    }
    return hashCode;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public <R> R accept(net.sourceforge.czt.util.Visitor<R> v)
  {
    if (v instanceof net.sourceforge.czt.oz.visitor.ClassTypeVisitor) {
      net.sourceforge.czt.oz.visitor.ClassTypeVisitor<R> visitor = 
      	  (net.sourceforge.czt.oz.visitor.ClassTypeVisitor<R>) v;
      return visitor.visitClassType(this);
    }
    return super.accept(v);
  }



  private
            		net.sourceforge.czt.oz.ast.ClassRefList
  classes_;

  public 
		net.sourceforge.czt.oz.ast.ClassRefList
  getClasses()
  {
    return classes_;
  }

  public void setClasses(
		net.sourceforge.czt.oz.ast.ClassRefList
	classes)
  {
                          classes_ = classes;
    }


  private
            		net.sourceforge.czt.z.ast.Signature
  state_;

  public 
		net.sourceforge.czt.z.ast.Signature
  getState()
  {
    return state_;
  }

  public void setState(
		net.sourceforge.czt.z.ast.Signature
	state)
  {
                          state_ = state;
    }


  private net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.z.ast.NameTypePair> attribute_ =
    new net.sourceforge.czt.base.impl.ListTermImpl<net.sourceforge.czt.z.ast.NameTypePair>();

  public net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.z.ast.NameTypePair> getAttribute()
  {
    return attribute_;
  }


  private net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.oz.ast.NameSignaturePair> operation_ =
    new net.sourceforge.czt.base.impl.ListTermImpl<net.sourceforge.czt.oz.ast.NameSignaturePair>();

  public net.sourceforge.czt.base.ast.ListTerm<net.sourceforge.czt.oz.ast.NameSignaturePair> getOperation()
  {
    return operation_;
  }
}
