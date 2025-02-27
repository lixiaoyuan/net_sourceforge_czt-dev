
/******************************************************************************
DO NOT EDIT THIS FILE!  THIS FILE WAS GENERATED BY GNAST
FROM THE TEMPLATE FILE JaxbToAst.vm.
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

package net.sourceforge.czt.zpatt.jaxb;


																																																				

/**
 * The unmarshaller responsible for deserializing XML data.
 *
 * @author GnAST version 1.6-cdh
 */
public class JaxbToAst extends net.sourceforge.czt.z.jaxb.JaxbToAst
{
  protected net.sourceforge.czt.zpatt.ast.ZpattFactory mZpattFactory_;

  public JaxbToAst()
  {
    mZpattFactory_ =
      new net.sourceforge.czt.zpatt.impl.ZpattFactoryImpl();
  }

  public JaxbToAst(net.sourceforge.czt.z.ast.ZFactory vZFactory, net.sourceforge.czt.zpatt.ast.ZpattFactory vZpattFactory)
  {
    super(vZFactory);
    mZpattFactory_ = vZpattFactory;
  }

  private static java.util.logging.Logger getLogger()
  {
    return java.util.logging.Logger.getLogger("net.sourceforge.czt.zpatt.jaxb.JaxbToAst");
  }

  public Object visitObject(Object object)
  {
    getLogger().fine("Visit " + object.getClass().toString());
    if (object instanceof String
        || object instanceof Boolean
        || object instanceof java.util.List
        || object instanceof Integer
        || object instanceof java.math.BigInteger
        || object instanceof net.sourceforge.czt.base.ast.Digit) {
      return object;
    }
    throw new UnsupportedOperationException("Unexpected element "
                                            + object.getClass().getName());
  }

  public Object visitJAXBElement(javax.xml.bind.JAXBElement<?> jaxbElement)
  {
    return dispatch(jaxbElement.getValue());
  }

  // TODO: why this restricted class is needed?
  public Object visitElementNSImpl(@SuppressWarnings("restriction") com.sun.org.apache.xerces.internal.dom.ElementNSImpl obj)
  {
    return obj;
  }


  public Object visitJokerNameList(net.sourceforge.czt.zpatt.jaxb.gen.JokerNameList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerNameList", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerNameList erg = mZpattFactory_.createJokerNameList(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerNameList", erg);
    return erg;
  }

  public Object visitJokerDeclList(net.sourceforge.czt.zpatt.jaxb.gen.JokerDeclList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerDeclList", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerDeclList erg = mZpattFactory_.createJokerDeclList(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerDeclList", erg);
    return erg;
  }

  public Object visitJokerNameListBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerNameListBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerNameListBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerNameList jokerNameList =
      (net.sourceforge.czt.zpatt.ast.JokerNameList) dispatch(jaxbObject.getJokerNameList());
    net.sourceforge.czt.z.ast.NameList nameList =
      (net.sourceforge.czt.z.ast.NameList) dispatch(jaxbObject.getNameList());
    net.sourceforge.czt.zpatt.ast.JokerNameListBinding erg = mZpattFactory_.createJokerNameListBinding(jokerNameList, nameList);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerNameListBinding", erg);
    return erg;
  }

  public Object visitJokerExprListBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerExprListBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerExprListBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerExprList jokerExprList =
      (net.sourceforge.czt.zpatt.ast.JokerExprList) dispatch(jaxbObject.getJokerExprList());
    net.sourceforge.czt.z.ast.ExprList exprList =
      (net.sourceforge.czt.z.ast.ExprList) dispatch(jaxbObject.getExprList());
    net.sourceforge.czt.zpatt.ast.JokerExprListBinding erg = mZpattFactory_.createJokerExprListBinding(jokerExprList, exprList);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerExprListBinding", erg);
    return erg;
  }

  public Object visitJokerExpr(net.sourceforge.czt.zpatt.jaxb.gen.JokerExpr jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerExpr", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerExpr erg = mZpattFactory_.createJokerExpr(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerExpr", erg);
    return erg;
  }

  public Object visitJokerStroke(net.sourceforge.czt.zpatt.jaxb.gen.JokerStroke jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerStroke", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerStroke erg = mZpattFactory_.createJokerStroke(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerStroke", erg);
    return erg;
  }

  public Object visitOracleAppl(net.sourceforge.czt.zpatt.jaxb.gen.OracleAppl jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitOracleAppl", jaxbObject);
            
    java.util.List<net.sourceforge.czt.zpatt.ast.Binding> binding = new java.util.Vector<net.sourceforge.czt.zpatt.ast.Binding>();
    
    for (
    				javax.xml.bind.JAXBElement<? extends net.sourceforge.czt.zpatt.jaxb.gen.Binding> 
					
		obj : jaxbObject.getBinding()) {
	  net.sourceforge.czt.zpatt.ast.Binding o = (net.sourceforge.czt.zpatt.ast.Binding) dispatch(obj);
	  binding.add(o);
	}
    
    net.sourceforge.czt.zpatt.ast.OracleAnswer oracleAnswer =
      (net.sourceforge.czt.zpatt.ast.OracleAnswer) dispatch(jaxbObject.getOracleAnswer());
    String name =
      (String) dispatch(jaxbObject.getName());
    net.sourceforge.czt.zpatt.ast.OracleAppl erg = mZpattFactory_.createOracleAppl(binding, oracleAnswer, name);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitOracleAppl", erg);
    return erg;
  }

  public Object visitJokerRenameListBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerRenameListBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerRenameListBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerRenameList jokerRenameList =
      (net.sourceforge.czt.zpatt.ast.JokerRenameList) dispatch(jaxbObject.getJokerRenameList());
    net.sourceforge.czt.z.ast.RenameList renameList =
      (net.sourceforge.czt.z.ast.RenameList) dispatch(jaxbObject.getRenameList());
    net.sourceforge.czt.zpatt.ast.JokerRenameListBinding erg = mZpattFactory_.createJokerRenameListBinding(jokerRenameList, renameList);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerRenameListBinding", erg);
    return erg;
  }

  public Object visitJokers(net.sourceforge.czt.zpatt.jaxb.gen.Jokers jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokers", jaxbObject);
            
    java.util.List<String> name = new java.util.Vector<String>();
    
    for (
    																String
					
		obj : jaxbObject.getName()) {
	  String o = (String) dispatch(obj);
	  name.add(o);
	}
    
																									    	    	    	    	    	    	                                    		
		
                    
    net.sourceforge.czt.zpatt.ast.JokerType jokerType = null;
    if (jaxbObject.getJokerType() != null) {
      String jokerTypeJaxb = jaxbObject.getJokerType().value();
            
      jokerType = net.sourceforge.czt.zpatt.ast.JokerType.valueOf(jokerTypeJaxb);
    }
    net.sourceforge.czt.zpatt.ast.Jokers erg = mZpattFactory_.createJokers(name, jokerType);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokers", erg);
    return erg;
  }

  public Object visitJokerName(net.sourceforge.czt.zpatt.jaxb.gen.JokerName jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerName", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerName erg = mZpattFactory_.createJokerName(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerName", erg);
    return erg;
  }

  public Object visitSequentList(net.sourceforge.czt.zpatt.jaxb.gen.SequentList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitSequentList", jaxbObject);
            
    java.util.List<net.sourceforge.czt.zpatt.ast.Sequent> sequent = new java.util.Vector<net.sourceforge.czt.zpatt.ast.Sequent>();
    
    for (
    				javax.xml.bind.JAXBElement<? extends net.sourceforge.czt.zpatt.jaxb.gen.Sequent> 
					
		obj : jaxbObject.getSequent()) {
	  net.sourceforge.czt.zpatt.ast.Sequent o = (net.sourceforge.czt.zpatt.ast.Sequent) dispatch(obj);
	  sequent.add(o);
	}
    
    net.sourceforge.czt.zpatt.ast.SequentList erg = mZpattFactory_.createSequentList(sequent);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitSequentList", erg);
    return erg;
  }

  public Object visitOracle(net.sourceforge.czt.zpatt.jaxb.gen.Oracle jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitOracle", jaxbObject);
    net.sourceforge.czt.zpatt.ast.Sequent sequent =
      (net.sourceforge.czt.zpatt.ast.Sequent) dispatch(jaxbObject.getSequent());
    String name =
      (String) dispatch(jaxbObject.getName());
    net.sourceforge.czt.zpatt.ast.Oracle erg = mZpattFactory_.createOracle(sequent, name);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitOracle", erg);
    return erg;
  }

  public Object visitHeadDeclList(net.sourceforge.czt.zpatt.jaxb.gen.HeadDeclList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitHeadDeclList", jaxbObject);
    net.sourceforge.czt.z.ast.ZDeclList zDeclList =
      (net.sourceforge.czt.z.ast.ZDeclList) dispatch(jaxbObject.getZDeclList());
    net.sourceforge.czt.zpatt.ast.JokerDeclList jokerDeclList =
      (net.sourceforge.czt.zpatt.ast.JokerDeclList) dispatch(jaxbObject.getJokerDeclList());
    net.sourceforge.czt.zpatt.ast.HeadDeclList erg = mZpattFactory_.createHeadDeclList(zDeclList, jokerDeclList);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitHeadDeclList", erg);
    return erg;
  }

  public Object visitJokerStrokeBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerStrokeBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerStrokeBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerStroke jokerStroke =
      (net.sourceforge.czt.zpatt.ast.JokerStroke) dispatch(jaxbObject.getJokerStroke());
    net.sourceforge.czt.z.ast.Stroke stroke =
      (net.sourceforge.czt.z.ast.Stroke) dispatch(jaxbObject.getStroke());
    net.sourceforge.czt.zpatt.ast.JokerStrokeBinding erg = mZpattFactory_.createJokerStrokeBinding(jokerStroke, stroke);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerStrokeBinding", erg);
    return erg;
  }

  public Object visitJokerPredBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerPredBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerPredBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerPred jokerPred =
      (net.sourceforge.czt.zpatt.ast.JokerPred) dispatch(jaxbObject.getJokerPred());
    net.sourceforge.czt.z.ast.Pred pred =
      (net.sourceforge.czt.z.ast.Pred) dispatch(jaxbObject.getPred());
    net.sourceforge.czt.zpatt.ast.JokerPredBinding erg = mZpattFactory_.createJokerPredBinding(jokerPred, pred);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerPredBinding", erg);
    return erg;
  }

  public Object visitJokerExprBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerExprBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerExprBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerExpr jokerExpr =
      (net.sourceforge.czt.zpatt.ast.JokerExpr) dispatch(jaxbObject.getJokerExpr());
    net.sourceforge.czt.z.ast.Expr expr =
      (net.sourceforge.czt.z.ast.Expr) dispatch(jaxbObject.getExpr());
    net.sourceforge.czt.zpatt.ast.JokerExprBinding erg = mZpattFactory_.createJokerExprBinding(jokerExpr, expr);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerExprBinding", erg);
    return erg;
  }

  public Object visitJokerNameBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerNameBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerNameBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerName jokerName =
      (net.sourceforge.czt.zpatt.ast.JokerName) dispatch(jaxbObject.getJokerName());
    net.sourceforge.czt.z.ast.Name name =
      (net.sourceforge.czt.z.ast.Name) dispatch(jaxbObject.getName());
    net.sourceforge.czt.zpatt.ast.JokerNameBinding erg = mZpattFactory_.createJokerNameBinding(jokerName, name);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerNameBinding", erg);
    return erg;
  }

  public Object visitCheckPassed(net.sourceforge.czt.zpatt.jaxb.gen.CheckPassed jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitCheckPassed", jaxbObject);
            
    java.util.List<net.sourceforge.czt.zpatt.ast.Binding> binding = new java.util.Vector<net.sourceforge.czt.zpatt.ast.Binding>();
    
    for (
    				javax.xml.bind.JAXBElement<? extends net.sourceforge.czt.zpatt.jaxb.gen.Binding> 
					
		obj : jaxbObject.getBinding()) {
	  net.sourceforge.czt.zpatt.ast.Binding o = (net.sourceforge.czt.zpatt.ast.Binding) dispatch(obj);
	  binding.add(o);
	}
    
    net.sourceforge.czt.zpatt.ast.CheckPassed erg = mZpattFactory_.createCheckPassed(binding);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitCheckPassed", erg);
    return erg;
  }

  public Object visitJokerRenameList(net.sourceforge.czt.zpatt.jaxb.gen.JokerRenameList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerRenameList", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerRenameList erg = mZpattFactory_.createJokerRenameList(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerRenameList", erg);
    return erg;
  }

  public Object visitJokerExprList(net.sourceforge.czt.zpatt.jaxb.gen.JokerExprList jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerExprList", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerExprList erg = mZpattFactory_.createJokerExprList(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerExprList", erg);
    return erg;
  }

  public Object visitRuleAppl(net.sourceforge.czt.zpatt.jaxb.gen.RuleAppl jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitRuleAppl", jaxbObject);
            
    java.util.List<net.sourceforge.czt.zpatt.ast.Binding> binding = new java.util.Vector<net.sourceforge.czt.zpatt.ast.Binding>();
    
    for (
    				javax.xml.bind.JAXBElement<? extends net.sourceforge.czt.zpatt.jaxb.gen.Binding> 
					
		obj : jaxbObject.getBinding()) {
	  net.sourceforge.czt.zpatt.ast.Binding o = (net.sourceforge.czt.zpatt.ast.Binding) dispatch(obj);
	  binding.add(o);
	}
    
    net.sourceforge.czt.zpatt.ast.SequentList sequentList =
      (net.sourceforge.czt.zpatt.ast.SequentList) dispatch(jaxbObject.getSequentList());
    String name =
      (String) dispatch(jaxbObject.getName());
    net.sourceforge.czt.zpatt.ast.RuleAppl erg = mZpattFactory_.createRuleAppl(binding, sequentList, name);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitRuleAppl", erg);
    return erg;
  }

  public Object visitSequent(net.sourceforge.czt.zpatt.jaxb.gen.Sequent jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitSequent", jaxbObject);
    net.sourceforge.czt.zpatt.ast.SequentContext sequentContext =
      (net.sourceforge.czt.zpatt.ast.SequentContext) dispatch(jaxbObject.getSequentContext());
    net.sourceforge.czt.z.ast.Pred pred =
      (net.sourceforge.czt.z.ast.Pred) dispatch(jaxbObject.getPred());
    net.sourceforge.czt.zpatt.ast.Sequent erg = mZpattFactory_.createSequent(sequentContext, pred);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitSequent", erg);
    return erg;
  }

  public Object visitSequentContext(net.sourceforge.czt.zpatt.jaxb.gen.SequentContext jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitSequentContext", jaxbObject);
    net.sourceforge.czt.zpatt.ast.SequentContext erg = mZpattFactory_.createSequentContext();

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitSequentContext", erg);
    return erg;
  }

  public Object visitRule(net.sourceforge.czt.zpatt.jaxb.gen.Rule jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitRule", jaxbObject);
    net.sourceforge.czt.zpatt.ast.Sequent sequent =
      (net.sourceforge.czt.zpatt.ast.Sequent) dispatch(jaxbObject.getSequent());
    String name =
      (String) dispatch(jaxbObject.getName());
    net.sourceforge.czt.zpatt.ast.SequentList premisses =
      (net.sourceforge.czt.zpatt.ast.SequentList) dispatch(jaxbObject.getPremisses());
    net.sourceforge.czt.zpatt.ast.Rule erg = mZpattFactory_.createRule(sequent, name, premisses);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitRule", erg);
    return erg;
  }

  public Object visitJokerDeclListBinding(net.sourceforge.czt.zpatt.jaxb.gen.JokerDeclListBinding jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerDeclListBinding", jaxbObject);
    net.sourceforge.czt.zpatt.ast.JokerDeclList jokerDeclList =
      (net.sourceforge.czt.zpatt.ast.JokerDeclList) dispatch(jaxbObject.getJokerDeclList());
    net.sourceforge.czt.z.ast.DeclList declList =
      (net.sourceforge.czt.z.ast.DeclList) dispatch(jaxbObject.getDeclList());
    net.sourceforge.czt.zpatt.ast.JokerDeclListBinding erg = mZpattFactory_.createJokerDeclListBinding(jokerDeclList, declList);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerDeclListBinding", erg);
    return erg;
  }

  public Object visitJokerPred(net.sourceforge.czt.zpatt.jaxb.gen.JokerPred jaxbObject)
  {
    getLogger().entering("JaxbToAst", "visitJokerPred", jaxbObject);
    String name =
      (String) dispatch(jaxbObject.getName());
    String id =
      (String) dispatch(jaxbObject.getId());
    net.sourceforge.czt.zpatt.ast.JokerPred erg = mZpattFactory_.createJokerPred(name, id);

    if (jaxbObject.getAnns() != null
    	// FindBugs points out this is unnecessary
        /*&& jaxbObject.getAnns().getAny() != null*/) {
      assert jaxbObject.getAnns().getAny() != null;
      java.util.List<Object> annsList = erg.getAnns();
      java.util.List<Object> anyList = jaxbObject.getAnns().getAny();
      for (Object obj : anyList) {
        Object o = dispatch(obj);
        annsList.add(o);
      }
    }

    getLogger().exiting("JaxbToAst", "visitJokerPred", erg);
    return erg;
  }
}
