
/*
  Copyright 2005, 2006, 2007 Leonardo Freitas
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

package net.sourceforge.czt.circuspatt.util;

import net.sourceforge.czt.zpatt.util.*;

/**
 * An interface for commonly used Circus characters.
 *
 * @author generated by Gnast XSL script circuspattchar2class.xsl
 */
public class CircusPattChar extends ZPattChar 
{
  public CircusPattChar(char[] chars)
  {
    super(chars);
  }



  /**
   * hollow version of JOKER char..
   */
  public static final CircusPattChar CIRCUSJOKERCHAR = new CircusPattChar(Character.toChars(0x2606));

  /**
   * hollow version of Rule char..
   */
  public static final CircusPattChar CIRCUSACTIONLAWCHAR = new CircusPattChar(Character.toChars(0x25A1));

  /**
   * hollow contained version of Rule char..
   */
  public static final CircusPattChar CIRCUSPROCESSLAWCHAR = new CircusPattChar(Character.toChars(0x25A3));
}
