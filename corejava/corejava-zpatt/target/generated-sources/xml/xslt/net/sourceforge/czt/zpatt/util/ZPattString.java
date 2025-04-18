
/**
Copyright 2003 Mark Utting
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

package net.sourceforge.czt.zpatt.util;

import net.sourceforge.czt.z.util.*;

/**
 * An interface for commonly used Z pattern characters.
 *
 * @author generated by Gnast XSL script zpattchar2stringclass.xsl
 */
public interface ZPattString extends ZString
{


  /**
   * black, right-pointing small triangle.
   */
  String PROVISO = String.valueOf(ZPattChar.PROVISO);

  /**
   * black star.
   */
  String JOKERCHAR = String.valueOf(ZPattChar.JOKERCHAR);

  /**
   * black square.
   */
  String RULECHAR = String.valueOf(ZPattChar.RULECHAR);

  /**
   * box drawing heavy horizontal.
   */
  String RULELINE = String.valueOf(ZPattChar.RULELINE);
  String JOKER = JOKERCHAR;
  String RULE = RULECHAR;
}

