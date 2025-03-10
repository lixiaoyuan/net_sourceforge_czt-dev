
/*
  Copyright 2003, 2004, 2005 Mark Utting
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

package net.sourceforge.czt.z.util;

/**
 * Commonly used Z characters and helper methods for Z characters.
 *
 * @author generated by Gnast XSL script zchar2class.xsl
 */
public class ZChar
{
  private static final int ASCII = 256;
  private final int codePoint_;

  public ZChar(char c)
  {
    if (Character.isHighSurrogate(c)) {
      throw new IllegalArgumentException();
    }
    codePoint_ = c;
  }

  public ZChar(int codePoint)
  {
    if (! Character.isValidCodePoint(codePoint)) {
      throw new IllegalArgumentException();
    }
    codePoint_ = codePoint;
  }

  protected ZChar(char high, char low)
  {
    if (! Character.isSurrogatePair(high, low)) {
      throw new IllegalArgumentException();
    }
    char[] chars = new char[2];
    chars[0] = high;
    chars[1] = low;
    codePoint_ = Character.codePointAt(chars, 0);
  }

  public ZChar(char[] chars)
  {
    codePoint_ = Character.codePointAt(chars, 0);
  }

  public int codePoint()
  {
    return codePoint_;
  }

  public int charCount()
  {
    return Character.charCount(codePoint_);
  }

  public boolean equals(Object obj)
  {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ZChar zchar = (ZChar) obj;
        return codePoint_ == zchar.codePoint_;
      }
    }
    return false;
  }
  
  public int hashCode()
  {
	int hashCode = super.hashCode();
    hashCode += codePoint_;
    return hashCode;
  }

  public String toString()
  {
    return String.valueOf(Character.toChars(codePoint_));
  }

  public static ZChar[] toZChars(String string)
  {
    final int numZChars = string.codePointCount(0, string.length());
    ZChar[] result = new ZChar[numZChars];
    int index = 0;
    for (int i = 0; i < numZChars; i++) {
      // This should work but it doesn't (perhaps a bug in Java 1.5?)
      //      final int index = string.offsetByCodePoints(0, i);
      final int codePoint = string.codePointAt(index);
      result[i] = new ZChar(codePoint);
      index = index + result[i].charCount();
    }
    return result;
  }

  public boolean isAsciiChar()
  {
    return codePoint_ < ASCII;
  }

  public static boolean isAlpha(ZChar zchar)
  {
    return isLetter(zchar) || isDigit(zchar);
  }

  public static boolean isDigit(ZChar zchar)
  {
    return Character.isDigit(zchar.codePoint());
  }

  public static boolean isLetter(ZChar zchar)
  {
    return Character.isLetter(zchar.codePoint());
  }

  public static boolean isStroke(ZChar zchar)
  {
    return zchar.equals(INSTROKE) || zchar.equals(OUTSTROKE) ||
      zchar.equals(PRIME);
  }

  public static boolean isWordGlue(ZChar zchar)
  {
    boolean isSE = zchar.equals(SE);
    boolean isSW = zchar.equals(SW);
    boolean isNE = zchar.equals(NE);
    boolean isNW = zchar.equals(NW);
    boolean isLL = zchar.equals(LL);
    return isSE || isSW || isNE || isNW || isLL;
  }



  /**
   * Greek capital letter delta.
   */
  public static final ZChar DELTA = new ZChar(0x0394);

  /**
   * Greek capital letter xi.
   */
  public static final ZChar XI = new ZChar(0x039E);

  /**
   * Greek small letter theta.
   */
  public static final ZChar THETA = new ZChar(0x03B8);

  /**
   * Greek small letter lambda.
   */
  public static final ZChar LAMBDA = new ZChar(0x03BB);

  /**
   * Greek small letter mu.
   */
  public static final ZChar MU = new ZChar(0x03BC);

  /**
   * arithmos, the set of all number-like objects.
   */
  public static final ZChar ARITHMOS = new ZChar(0x1D538);

  /**
   * the set of natural numbers (0..).
   */
  public static final ZChar NAT = new ZChar(0x2115);

  /**
   * power set.
   */
  public static final ZChar POWER = new ZChar(0x2119);

  /**
   * prime.
   */
  public static final ZChar PRIME = new ZChar(0x2032);

  /**
   * exclamation mark.
   */
  public static final ZChar OUTSTROKE = new ZChar(0x0021);

  /**
   * question mark.
   */
  public static final ZChar INSTROKE = new ZChar(0x003F);

  /**
   * north east arrow.
   */
  public static final ZChar NE = new ZChar(0x2197);

  /**
   * south west arrow.
   */
  public static final ZChar SW = new ZChar(0x2199);

  /**
   * south east arrow.
   */
  public static final ZChar SE = new ZChar(0x2198);

  /**
   * north west arrow.
   */
  public static final ZChar NW = new ZChar(0x2196);

  /**
   * low line.
   */
  public static final ZChar LL = new ZChar(0x005F);

  /**
   * left parenthesis.
   */
  public static final ZChar LPAREN = new ZChar(0x0028);

  /**
   * right parenthesis.
   */
  public static final ZChar RPAREN = new ZChar(0x0029);

  /**
   * left square bracket.
   */
  public static final ZChar LSQUARE = new ZChar(0x005B);

  /**
   * right square bracket.
   */
  public static final ZChar RSQUARE = new ZChar(0x005D);

  /**
   * left curly bracket.
   */
  public static final ZChar LBRACE = new ZChar(0x007B);

  /**
   * right curly bracket.
   */
  public static final ZChar RBRACE = new ZChar(0x007D);

  /**
   * left binding bracket.
   */
  public static final ZChar LBIND = new ZChar(0x2989);

  /**
   * right binding bracket.
   */
  public static final ZChar RBIND = new ZChar(0x298A);

  /**
   * left angle bracket for use in free type definitions.
   */
  public static final ZChar LDATA = new ZChar(0x27EA);

  /**
   * left angle bracket for use in free type definitions.
   */
  public static final ZChar RDATA = new ZChar(0x27EB);

  /**
   * start a Z paragraph.
   */
  public static final ZChar ZEDCHAR = new ZChar(0x2500);

  /**
   * start a Z axiomatic paragraph.
   */
  public static final ZChar AXCHAR = new ZChar(0x2577);

  /**
   * start a Z schema definition.
   */
  public static final ZChar SCHCHAR = new ZChar(0x250C);

  /**
   * start a generic Z axiomatic paragraph.
   */
  public static final ZChar GENCHAR = new ZChar(0x2550);

  /**
   * end a Z paragraph.
   */
  public static final ZChar ENDCHAR = new ZChar(0x2514);

  /**
   * line separator.
   */
  public static final ZChar NLCHAR = new ZChar(0x2028);

  /**
   * space.
   */
  public static final ZChar SPACE = new ZChar(0x0020);

  /**
   * vertical line.
   */
  public static final ZChar VL = new ZChar(0x007C);

  /**
   * ampersand.
   */
  public static final ZChar AMP = new ZChar(0x0026);

  /**
   * conjecture.
   */
  public static final ZChar VDASH = new ZChar(0x22A2);

  /**
   * logical and.
   */
  public static final ZChar AND = new ZChar(0x2227);

  /**
   * logical or.
   */
  public static final ZChar OR = new ZChar(0x2228);

  /**
   * logical implication.
   */
  public static final ZChar IMP = new ZChar(0x21D2);

  /**
   * logical equivalence.
   */
  public static final ZChar IFF = new ZChar(0x21D4);

  /**
   * logical negation.
   */
  public static final ZChar NOT = new ZChar(0x00AC);

  /**
   * for all.
   */
  public static final ZChar ALL = new ZChar(0x2200);

  /**
   * there exists.
   */
  public static final ZChar EXI = new ZChar(0x2203);

  /**
   * cartesian product.
   */
  public static final ZChar CROSS = new ZChar(0x00D7);

  /**
   * solidus forward slash character.
   */
  public static final ZChar SOLIDUS = new ZChar(0x002F);

  /**
   * equals sign.
   */
  public static final ZChar EQUALS = new ZChar(0x003D);

  /**
   * element of.
   */
  public static final ZChar MEM = new ZChar(0x2208);

  /**
   * colon.
   */
  public static final ZChar COLON = new ZChar(0x003A);

  /**
   * semicolon.
   */
  public static final ZChar SEMICOLON = new ZChar(0x003B);

  /**
   * comma.
   */
  public static final ZChar COMMA = new ZChar(0x002C);

  /**
   * full stop.
   */
  public static final ZChar DOT = new ZChar(0x002E);

  /**
   * Z notation spot.
   */
  public static final ZChar SPOT = new ZChar(0x2981);

  /**
   * schema hiding.
   */
  public static final ZChar ZHIDE = new ZChar(0x29F9);

  /**
   * schema projection.
   */
  public static final ZChar ZPROJ = new ZChar(0x2A21);

  /**
   * schema composition.
   */
  public static final ZChar ZCOMP = new ZChar(0x2A1F);

  /**
   * schema piping.
   */
  public static final ZChar ZPIPE = new ZChar(0x2A20);

  /**
   * plus sign.
   */
  public static final ZChar PLUS = new ZChar(0x002B);

  /**
   * Z notation type colon.
   */
  public static final ZChar TYPECOLON = new ZChar(0x2982);

  /**
   * relation.
   */
  public static final ZChar REL = new ZChar(0x2194);

  /**
   * total function.
   */
  public static final ZChar FUN = new ZChar(0x2192);

  /**
   * not equal to.
   */
  public static final ZChar NEQ = new ZChar(0x2260);

  /**
   * not an element of.
   */
  public static final ZChar NOTMEM = new ZChar(0x2209);

  /**
   * empty set.
   */
  public static final ZChar EMPTYSET = new ZChar(0x2205);

  /**
   * subset of or equal to.
   */
  public static final ZChar SUBSETEQ = new ZChar(0x2286);

  /**
   * subset of.
   */
  public static final ZChar SUBSET = new ZChar(0x2282);

  /**
   * union.
   */
  public static final ZChar CUP = new ZChar(0x222A);

  /**
   * intersection.
   */
  public static final ZChar CAP = new ZChar(0x2229);

  /**
   * set minus.
   */
  public static final ZChar SETMINUS = new ZChar(0x2216);

  /**
   * set symmetric difference.
   */
  public static final ZChar SYMDIFF = new ZChar(0x2296);

  /**
   * n-ary union.
   */
  public static final ZChar BIGCUP = new ZChar(0x22C3);

  /**
   * n-ary intersection.
   */
  public static final ZChar BIGCAP = new ZChar(0x22C2);

  /**
   * finite set.
   */
  public static final ZChar FINSET = new ZChar(0x1D53D);

  /**
   * maplet, forms a pair.
   */
  public static final ZChar MAPSTO = new ZChar(0x21A6);

  /**
   * relational composition.
   */
  public static final ZChar COMP = new ZChar(0x2A3E);

  /**
   * functional composition.
   */
  public static final ZChar CIRC = new ZChar(0x2218);

  /**
   * domain restriction.
   */
  public static final ZChar DRES = new ZChar(0x25C1);

  /**
   * range restriction.
   */
  public static final ZChar RRES = new ZChar(0x25B7);

  /**
   * domain subtraction.
   */
  public static final ZChar NDRES = new ZChar(0x2A64);

  /**
   * range subtraction.
   */
  public static final ZChar NRRES = new ZChar(0x2A65);

  /**
   * relational inversion.
   */
  public static final ZChar TILDE = new ZChar(0x223C);

  /**
   * left relational image bracket.
   */
  public static final ZChar LIMG = new ZChar(0x2987);

  /**
   * right relational image bracket.
   */
  public static final ZChar RIMG = new ZChar(0x2988);

  /**
   * relational override.
   */
  public static final ZChar OPLUS = new ZChar(0x2295);

  /**
   * partial function.
   */
  public static final ZChar PFUN = new ZChar(0x21F8);

  /**
   * partial injective (1-1) function.
   */
  public static final ZChar PINJ = new ZChar(0x2914);

  /**
   * total injective (1-1) function.
   */
  public static final ZChar INJ = new ZChar(0x21A3);

  /**
   * partial surjective (onto) function.
   */
  public static final ZChar PSURJ = new ZChar(0x2900);

  /**
   * total surjective (onto) function.
   */
  public static final ZChar SURJ = new ZChar(0x21A0);

  /**
   * bijective (1-1 and onto) function.
   */
  public static final ZChar BIJ = new ZChar(0x2916);

  /**
   * finite function.
   */
  public static final ZChar FFUN = new ZChar(0x21FB);

  /**
   * finite injective function.
   */
  public static final ZChar FINJ = new ZChar(0x2915);

  /**
   * set of integers.
   */
  public static final ZChar NUM = new ZChar(0x2124);

  /**
   * prefix numeric negation.
   */
  public static final ZChar NEG = new ZChar(0x002D);

  /**
   * infix minus sign.
   */
  public static final ZChar MINUS = new ZChar(0x2212);

  /**
   * less-than or equal to.
   */
  public static final ZChar LEQ = new ZChar(0x2264);

  /**
   * less-than sign.
   */
  public static final ZChar LESS = new ZChar(0x003C);

  /**
   * greater-than or equal to.
   */
  public static final ZChar GEQ = new ZChar(0x2265);

  /**
   * greater-than sign.
   */
  public static final ZChar GREATER = new ZChar(0x003E);

  /**
   * asterisk (multiplication sign).
   */
  public static final ZChar MULT = new ZChar(0x002A);

  /**
   * set cardinality.
   */
  public static final ZChar NUMBER = new ZChar(0x0023);

  /**
   * left sequence bracket.
   */
  public static final ZChar LANGLE = new ZChar(0x27E8);

  /**
   * right sequence bracket.
   */
  public static final ZChar RANGLE = new ZChar(0x27E9);

  /**
   * sequence concatenation.
   */
  public static final ZChar CAT = new ZChar(0x2040);

  /**
   * domain restriction for sequences.
   */
  public static final ZChar EXTRACT = new ZChar(0x21BF);

  /**
   * range restriction for sequences.
   */
  public static final ZChar FILTER = new ZChar(0x21BE);
}
