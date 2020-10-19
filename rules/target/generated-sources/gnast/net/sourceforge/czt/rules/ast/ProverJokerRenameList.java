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

import net.sourceforge.czt.base.ast.Term;
import net.sourceforge.czt.rules.Joker;
import net.sourceforge.czt.z.ast.*;
import net.sourceforge.czt.zpatt.ast.*;
import net.sourceforge.czt.zpatt.impl.JokerRenameListImpl;

/**
 * An implementation of the JokerRenameList and Joker interface.
 *
 * @author Petra Malik
 */
public class ProverJokerRenameList
  extends JokerRenameListImpl
  implements Joker
{
  private JokerRenameListBinding binding_;

  protected ProverJokerRenameList(String name, String id)
  {
    super.setName(name);
    super.setId(id);
    binding_ = new ProverJokerRenameListBinding(this);
  }

  public Term boundTo()
  {
    return getBinding().getRenameList();
  }

  public Binding bind(Term term)
  {
    if (! (term instanceof RenameList)) {
      String message = "Cannot bind " + term + " to a JokerRenameList";
      throw new IllegalArgumentException(message);
    }
    RenameList vRenameList = (RenameList) term;
    getBinding().setRenameList(vRenameList);
    return getBinding();
  }

  public JokerRenameListBinding getBinding()
  {
    return binding_;
  }

  public void setName(String name)
  {
    throw new UnsupportedOperationException();
  }

  public ProverJokerRenameList create(Object[] args)
  {
    throw new UnsupportedOperationException();
  }
}
