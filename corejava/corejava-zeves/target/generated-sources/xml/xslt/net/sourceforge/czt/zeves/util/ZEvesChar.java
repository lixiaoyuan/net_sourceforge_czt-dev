
/*
  Copyright 2011, Leo Freitas
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

package net.sourceforge.czt.zeves.util;

import net.sourceforge.czt.z.util.*;

/**
 * An interface for commonly used Z Eves proofs characters.
 *
 * @author generated by Gnast XSL script zeveschar2class.xsl
 */
public class ZEvesChar extends ZChar
{
  public ZEvesChar(char[] chars)
  {
    super(chars);
  }

  public static boolean isZProofDollar(ZChar zchar)
  {
    return zchar.equals(ZEvesChar.ZPROOFDOLLARCHAR) ||
           (zchar.codePoint() == ZEvesChar.ZPROOFDOLLARCHAR.codePoint() &&
            zchar.charCount() == ZEvesChar.ZPROOFDOLLARCHAR.charCount());
  }



  /**
   * dollar sign for special thm names.
   */
  public static final ZEvesChar ZPROOFDOLLARCHAR = new ZEvesChar(Character.toChars(0x0024));

  /**
   * big turnstyle not bold.
   */
  public static final ZEvesChar ZPROOFCHAR = new ZEvesChar(Character.toChars(0x251C));

  /**
   * big turnstyle bold.
   */
  public static final ZEvesChar ZPROOFSECTIONCHAR = new ZEvesChar(Character.toChars(0x2523));

  /**
   * disabled theorem character marker (boxed cross).
   */
  public static final ZEvesChar DISABLEDTHMTAGCHAR = new ZEvesChar(Character.toChars(0x2612));

  /**
   * disabled paragraph definition character marker (unboxed cross).
   */
  public static final ZEvesChar DISABLEDDEFTAGCHAR = new ZEvesChar(Character.toChars(0x2613));

  /**
   * left label bracket.
   */
  public static final ZEvesChar LLABEL = new ZEvesChar(Character.toChars(0x300A));

  /**
   * right label bracket.
   */
  public static final ZEvesChar RLABEL = new ZEvesChar(Character.toChars(0x300B));

  /**
   * left znote bracket.
   */
  public static final ZEvesChar LZNOTE = new ZEvesChar(Character.toChars(0x231C));

  /**
   * right znote bracket.
   */
  public static final ZEvesChar RZNOTE = new ZEvesChar(Character.toChars(0x231D));

  /**
   * left bag display bracket.
   */
  public static final ZEvesChar LBAG = new ZEvesChar(Character.toChars(0x27E6));

  /**
   * right bag display bracket.
   */
  public static final ZEvesChar RBAG = new ZEvesChar(Character.toChars(0x27E7));

  /**
   * bag count.
   */
  public static final ZEvesChar BCOUNT = new ZEvesChar(Character.toChars(0x266F));

  /**
   * bag scalling.
   */
  public static final ZEvesChar OTIMES = new ZEvesChar(Character.toChars(0x2297));

  /**
   * bag containment.
   */
  public static final ZEvesChar INBAG = new ZEvesChar(Character.toChars(0x22FF));

  /**
   * bag subset equal.
   */
  public static final ZEvesChar SUBBAGEQ = new ZEvesChar(Character.toChars(0x2291));

  /**
   * bag union.
   */
  public static final ZEvesChar UPLUS = new ZEvesChar(Character.toChars(0x228E));

  /**
   * bag difference.
   */
  public static final ZEvesChar UMINUS = new ZEvesChar(Character.toChars(0x2A41));
}
