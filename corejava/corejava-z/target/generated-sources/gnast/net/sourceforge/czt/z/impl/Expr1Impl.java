
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

package net.sourceforge.czt.z.impl;

																																																				

/**
 * An implementation of the interface
 * {@link net.sourceforge.czt.z.ast.Expr1}.
 *
 * @author GnAST version 1.6-cdh
 */
public abstract class Expr1Impl
extends 		 		net.sourceforge.czt.z.impl.ExprImpl
 	  implements net.sourceforge.czt.z.ast.Expr1
{






  /**
   * The default constructor.
   *
   * Do not use it explicitly, unless you are extending this class.
   * If you want to create an instance of this class, please use the
   * {@link net.sourceforge.czt.z.ast.ZFactory object factory}.
   */
  protected Expr1Impl()
  {
    this(null);
  }

  protected Expr1Impl(net.sourceforge.czt.base.impl.BaseFactory factory)
  {
    super(factory);
  }
  
  
  /**
   * Compares the specified object with this Expr1Impl
   * for equality.  Returns true if and only if the specified object is
   * also a(n) Expr1Impl and all the getter methods except getAnns
   * return equal objects.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass()) && super.equals(obj)) {
      	// if foreach on meta-model (.vm) file is empty, this won't be used.
      	//@SuppressWarnings("unused")
        //Expr1Impl object = ((Expr1Impl) obj);
        if (expr_ != null) {
          if (!expr_.equals(((Expr1Impl) obj).expr_)) {
            return false;
          }
        }
        else {
          if (((Expr1Impl) obj).expr_ != null) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the hash code value for this Expr1Impl.
   */
  @Override
  public int hashCode()
  {
    //final int constant = 31;

    int hashCode = super.hashCode();
    hashCode += "Expr1Impl".hashCode();
    if (expr_ != null) {
      hashCode += 31 * expr_.hashCode();
    }
    return hashCode;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public <R> R accept(net.sourceforge.czt.util.Visitor<R> v)
  {
    if (v instanceof net.sourceforge.czt.z.visitor.Expr1Visitor) {
      net.sourceforge.czt.z.visitor.Expr1Visitor<R> visitor = 
      	  (net.sourceforge.czt.z.visitor.Expr1Visitor<R>) v;
      return visitor.visitExpr1(this);
    }
    return super.accept(v);
  }



  private
            		net.sourceforge.czt.z.ast.Expr
  expr_;

  public 
		net.sourceforge.czt.z.ast.Expr
  getExpr()
  {
    return expr_;
  }

  public void setExpr(
		net.sourceforge.czt.z.ast.Expr
	expr)
  {
                          expr_ = expr;
    }
}
