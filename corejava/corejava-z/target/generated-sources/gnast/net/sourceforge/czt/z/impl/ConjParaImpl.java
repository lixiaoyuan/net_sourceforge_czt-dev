
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
 * {@link net.sourceforge.czt.z.ast.ConjPara}.
 *
 * @author GnAST version 1.6-cdh
 */
public class ConjParaImpl
extends 		 		net.sourceforge.czt.z.impl.ParaImpl
 	  implements net.sourceforge.czt.z.ast.ConjPara
{





  /**
   * static instance count for ConjParaImpl
   */
   private static long instanceCount_ = 0;
   
  /**
   * public attribute determining whether to log to the 
   * standard output information about who is creating 
   * this instance of ConjParaImpl (e.g., ic >= sl)
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
   * {@link net.sourceforge.czt.z.ast.ZFactory object factory}.
   */
  protected ConjParaImpl()
  {
    this(null);
  }

  protected ConjParaImpl(net.sourceforge.czt.base.impl.BaseFactory factory)
  {
    super(factory);
    countInstance();
  }
  
  private static synchronized void countInstance()
  {
	  instanceCount_++;
  }
  
  /**
   * Compares the specified object with this ConjParaImpl
   * for equality.  Returns true if and only if the specified object is
   * also a(n) ConjParaImpl and all the getter methods except getAnns
   * return equal objects.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass()) && super.equals(obj)) {
      	// if foreach on meta-model (.vm) file is empty, this won't be used.
      	//@SuppressWarnings("unused")
        //ConjParaImpl object = ((ConjParaImpl) obj);
        if (nameList_ != null) {
          if (!nameList_.equals(((ConjParaImpl) obj).nameList_)) {
            return false;
          }
        }
        else {
          if (((ConjParaImpl) obj).nameList_ != null) {
            return false;
          }
        }
        if (pred_ != null) {
          if (!pred_.equals(((ConjParaImpl) obj).pred_)) {
            return false;
          }
        }
        else {
          if (((ConjParaImpl) obj).pred_ != null) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the hash code value for this ConjParaImpl.
   */
  @Override
  public int hashCode()
  {
    //final int constant = 31;

    int hashCode = super.hashCode();
    hashCode += "ConjParaImpl".hashCode();
    if (nameList_ != null) {
      hashCode += 31 * nameList_.hashCode();
    }
    if (pred_ != null) {
      hashCode += 31 * pred_.hashCode();
    }
    return hashCode;
  }

  /**
   * Accepts a visitor.
   */
  @Override
  public <R> R accept(net.sourceforge.czt.util.Visitor<R> v)
  {
    if (v instanceof net.sourceforge.czt.z.visitor.ConjParaVisitor) {
      net.sourceforge.czt.z.visitor.ConjParaVisitor<R> visitor = 
      	  (net.sourceforge.czt.z.visitor.ConjParaVisitor<R>) v;
      return visitor.visitConjPara(this);
    }
    return super.accept(v);
  }

  /**
   * Returns a new object of this class.
   */
  public ConjParaImpl create(Object[] args)
  {
    ConjParaImpl zedObject = null;
    try {
	  	  			  	net.sourceforge.czt.z.ast.NameList nameList = (
						net.sourceforge.czt.z.ast.NameList) args[0];
	  	  	  			  	net.sourceforge.czt.z.ast.Pred pred = (
						net.sourceforge.czt.z.ast.Pred) args[1];
	        zedObject = new ConjParaImpl(getFactory());
      zedObject.setNameList(nameList);
      zedObject.setPred(pred);
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
    Object[] erg = { getNameList(), getPred() };
    return erg;
  }
  
  /** Only concrete classes get the instance count method visible */
  // JokerCommunication in circuspatt extends CommunicationImpl which is not abstract? weird...
  public static /*final*/ long instanceCount()
  {
    return instanceCount_;
  }


  private
            		net.sourceforge.czt.z.ast.NameList
  nameList_;

  public 
		net.sourceforge.czt.z.ast.NameList
  getNameList()
  {
    return nameList_;
  }

  public void setNameList(
		net.sourceforge.czt.z.ast.NameList
	nameList)
  {
                          nameList_ = nameList;
    }
  /**
   * This is a convenience method.
   * It returns a ZNameList if #getNameList
   * returns an instance of ZNameList
   * and throws an UnsupportedAstClassException otherwise.
   */
  public net.sourceforge.czt.z.ast.ZNameList getZNameList()
  {
    net.sourceforge.czt.z.ast.NameList object = getNameList();
    if (object instanceof net.sourceforge.czt.z.ast.ZNameList) {
      return (net.sourceforge.czt.z.ast.ZNameList) object;
    }
    final String message = "Expected the Z implementation of net.sourceforge.czt.z.ast.NameList" +
      " but found " + String.valueOf(object);
    throw new net.sourceforge.czt.base.util.UnsupportedAstClassException(message);
  }


  private
            		net.sourceforge.czt.z.ast.Pred
  pred_;

  public 
		net.sourceforge.czt.z.ast.Pred
  getPred()
  {
    return pred_;
  }

  public void setPred(
		net.sourceforge.czt.z.ast.Pred
	pred)
  {
                          pred_ = pred;
    }

  /**
   * This is a convenience method for getting the name of a conjecture.
   * Conjecture names are currently optional at the Unicode level, and 
   * are stored as annotations rather than as normal entries in the
   * CZT XML format.  This method returns a non-null String if the conjecture
   * has a name annotation, or null otherwise.  If the Z standard is
   * extended in the future so that conjecture names are compulsory
   * in the Unicode markup (as they are in the LaTeX markup), then
   * this method may be replaced by an equivalent method that
   * is automatically generated from the CZT XML schema.
   */
  public String getName()
  {
	  net.sourceforge.czt.z.ast.Name name = this.getAnn(net.sourceforge.czt.z.ast.Name.class);
    if (name instanceof net.sourceforge.czt.z.ast.ZName) {
      return ((net.sourceforge.czt.z.ast.ZName) name).getWord();
    }
    return null;
  }
  
  public void setName(net.sourceforge.czt.z.ast.Name name)
  {
	  net.sourceforge.czt.z.ast.Name zname = this.getAnn(net.sourceforge.czt.z.ast.Name.class);
    if (zname != null)
    {      
      java.util.List<Object> anns = getAnns();
      for (java.util.Iterator<Object> iter = anns.iterator(); iter.hasNext(); )
      {
        Object ann = iter.next();
        if (net.sourceforge.czt.z.ast.Name.class.isInstance(ann))
        {
          iter.remove();
        }
      }
    }
    this.getAnns().add(name);
  }
}
