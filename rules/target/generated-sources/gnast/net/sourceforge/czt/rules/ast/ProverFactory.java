/*
  Copyright (C) 2005, 2006, 2007 Petra Malik
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
*/

package net.sourceforge.czt.rules.ast;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.czt.zpatt.ast.*;
import net.sourceforge.czt.zpatt.impl.ZpattFactoryImpl;

/**
 * Makes sure that Joker are only created once.
 *
 * @author Petra Malik
 */
public class ProverFactory
  extends ZpattFactoryImpl
{
  private static int id_ = 0;
  private Map<String, ProverJokerDeclList> mJokerDeclLists_ =
    new HashMap<String, ProverJokerDeclList>();
  private Map<String, ProverJokerExprList> mJokerExprLists_ =
    new HashMap<String, ProverJokerExprList>();
  private Map<String, ProverJokerNameList> mJokerNameLists_ =
    new HashMap<String, ProverJokerNameList>();
  private Map<String, ProverJokerRenameList> mJokerRenameLists_ =
    new HashMap<String, ProverJokerRenameList>();
  private Map<String, ProverJokerStroke> mJokerStrokes_ =
    new HashMap<String, ProverJokerStroke>();
  private Map<String, ProverJokerName> mJokerNames_ =
    new HashMap<String, ProverJokerName>();
  private Map<String, ProverJokerPred> mJokerPreds_ =
    new HashMap<String, ProverJokerPred>();
  private Map<String, ProverJokerExpr> mJokerExprs_ =
    new HashMap<String, ProverJokerExpr>();

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerDeclList createJokerDeclList()
  {
    throw new UnsupportedOperationException();
  }

  public JokerDeclList createJokerDeclList(String name, String id)
  {
    ProverJokerDeclList result = mJokerDeclLists_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerDeclList(name, newId);
      countInstance();
      mJokerDeclLists_.put(name, result);
    }
    return result;
  }

  public JokerDeclListBinding createJokerDeclListBinding()
  {
    String message =
      "The JokerDeclList for the new JokerDeclListBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerDeclListBinding createJokerDeclListBinding(JokerDeclList binding)
  {
    String name = binding.getName();
    ProverJokerDeclList joker = mJokerDeclLists_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerExprList createJokerExprList()
  {
    throw new UnsupportedOperationException();
  }

  public JokerExprList createJokerExprList(String name, String id)
  {
    ProverJokerExprList result = mJokerExprLists_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerExprList(name, newId);
      countInstance();
      mJokerExprLists_.put(name, result);
    }
    return result;
  }

  public JokerExprListBinding createJokerExprListBinding()
  {
    String message =
      "The JokerExprList for the new JokerExprListBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerExprListBinding createJokerExprListBinding(JokerExprList binding)
  {
    String name = binding.getName();
    ProverJokerExprList joker = mJokerExprLists_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerNameList createJokerNameList()
  {
    throw new UnsupportedOperationException();
  }

  public JokerNameList createJokerNameList(String name, String id)
  {
    ProverJokerNameList result = mJokerNameLists_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerNameList(name, newId);
      countInstance();
      mJokerNameLists_.put(name, result);
    }
    return result;
  }

  public JokerNameListBinding createJokerNameListBinding()
  {
    String message =
      "The JokerNameList for the new JokerNameListBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerNameListBinding createJokerNameListBinding(JokerNameList binding)
  {
    String name = binding.getName();
    ProverJokerNameList joker = mJokerNameLists_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerRenameList createJokerRenameList()
  {
    throw new UnsupportedOperationException();
  }

  public JokerRenameList createJokerRenameList(String name, String id)
  {
    ProverJokerRenameList result = mJokerRenameLists_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerRenameList(name, newId);
      countInstance();
      mJokerRenameLists_.put(name, result);
    }
    return result;
  }

  public JokerRenameListBinding createJokerRenameListBinding()
  {
    String message =
      "The JokerRenameList for the new JokerRenameListBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerRenameListBinding createJokerRenameListBinding(JokerRenameList binding)
  {
    String name = binding.getName();
    ProverJokerRenameList joker = mJokerRenameLists_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerStroke createJokerStroke()
  {
    throw new UnsupportedOperationException();
  }

  public JokerStroke createJokerStroke(String name, String id)
  {
    ProverJokerStroke result = mJokerStrokes_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerStroke(name, newId);
      countInstance();
      mJokerStrokes_.put(name, result);
    }
    return result;
  }

  public JokerStrokeBinding createJokerStrokeBinding()
  {
    String message =
      "The JokerStroke for the new JokerStrokeBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerStrokeBinding createJokerStrokeBinding(JokerStroke binding)
  {
    String name = binding.getName();
    ProverJokerStroke joker = mJokerStrokes_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerName createJokerName()
  {
    throw new UnsupportedOperationException();
  }

  public JokerName createJokerName(String name, String id)
  {
    ProverJokerName result = mJokerNames_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerName(name, newId);
      countInstance();
      mJokerNames_.put(name, result);
    }
    return result;
  }

  public JokerNameBinding createJokerNameBinding()
  {
    String message =
      "The JokerName for the new JokerNameBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerNameBinding createJokerNameBinding(JokerName binding)
  {
    String name = binding.getName();
    ProverJokerName joker = mJokerNames_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerPred createJokerPred()
  {
    throw new UnsupportedOperationException();
  }

  public JokerPred createJokerPred(String name, String id)
  {
    ProverJokerPred result = mJokerPreds_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerPred(name, newId);
      countInstance();
      mJokerPreds_.put(name, result);
    }
    return result;
  }

  public JokerPredBinding createJokerPredBinding()
  {
    String message =
      "The JokerPred for the new JokerPredBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerPredBinding createJokerPredBinding(JokerPred binding)
  {
    String name = binding.getName();
    ProverJokerPred joker = mJokerPreds_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

  /**
   * Throws an UnsupportedOperationException.
   */
  public JokerExpr createJokerExpr()
  {
    throw new UnsupportedOperationException();
  }

  public JokerExpr createJokerExpr(String name, String id)
  {
    ProverJokerExpr result = mJokerExprs_.get(name);
    if (result == null) {
      String newId = "" + id_++;
      result = new  ProverJokerExpr(name, newId);
      countInstance();
      mJokerExprs_.put(name, result);
    }
    return result;
  }

  public JokerExprBinding createJokerExprBinding()
  {
    String message =
      "The JokerExpr for the new JokerExprBinding must be given";
    throw new UnsupportedOperationException(message);
  }

  public JokerExprBinding createJokerExprBinding(JokerExpr binding)
  {
    String name = binding.getName();
    ProverJokerExpr joker = mJokerExprs_.get(name);
    if (joker == null) {
      throw new IllegalArgumentException("Unknown joker " + name);
    }
    return joker.getBinding();
  }

}
