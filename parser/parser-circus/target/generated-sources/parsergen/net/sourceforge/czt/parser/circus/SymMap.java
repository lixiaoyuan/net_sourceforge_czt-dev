
/*
  Copyright (C) 2006, 2007 Petra Malik
  This file is part of the czt project.

  The czt project contains free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation); either version 2 of the License, or
  (at your option) any later version.

  The czt project is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY); without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with czt); if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package net.sourceforge.czt.parser.circus;

import java.util.Map;
import net.sourceforge.czt.session.Dialect;

public class SymMap
{
  private static Map<String,Integer> MAP =
    net.sourceforge.czt.parser.z.SymMap.createMap(Sym.class);

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  static final Dialect dialect_ = 
  						Dialect.CIRCUS
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  private SymMap()
  {
  }

  public static Map<String,Integer> getMap()
  {
    return MAP;
  }
}
