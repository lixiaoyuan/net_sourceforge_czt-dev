/* The following code was generated by JFlex 1.4.3 on 10/18/20 8:39 AM */


/*
  Copyright 2003, 2006, 2007 Petra Malik
  This file is part of the czt project.

  The czt project contains free software;
  you can redistribute it and/or modify
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

/*
  Numbers in brackets contained in comments refer to
  the corresponding sections in the Z Standard.
*/

/* --------------------------Usercode Section------------------------ */
package net.sourceforge.czt.parser.circusconf;

import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.CztReader;
import net.sourceforge.czt.parser.util.Decorword;
import net.sourceforge.czt.parser.util.LocInfo;
import net.sourceforge.czt.parser.util.LocInfoImpl;
import net.sourceforge.czt.parser.util.LocInt;
import net.sourceforge.czt.parser.util.LocString;
import net.sourceforge.czt.parser.util.ScanException;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.session.Source;


/**
 * <p>The context-free lexer for
 * specifications in unicode format.</p>
 *
 * @author Petra Malik
 * @see net.sourceforge.czt.parser.z
 */
// this is because of dangling fields from JFlex like zzAtBOL, zzEOFDone, etc 
// that are never used in certain kind of lexers created
@SuppressWarnings("unused")

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 10/18/20 8:39 AM from the specification file
 * <tt>/root/czt-code/parser/parser-circusconf/target/generated-sources/parsergen-jflex/net/sourceforge/czt/parser/circusconf/ContextFreeScanner.jflex</tt>
 */
public class ContextFreeScanner implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int Z = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\56\1\57\2\0\1\60\22\0\1\56\1\7\6\0\1\14"+
    "\1\15\2\0\1\5\1\0\1\5\1\0\12\1\1\5\1\5\1\0"+
    "\1\61\1\0\1\6\1\0\32\2\1\16\1\0\1\17\1\0\1\11"+
    "\1\0\32\2\1\20\1\0\1\21\54\0\1\2\12\0\1\2\4\0"+
    "\1\2\5\0\27\2\1\0\37\2\1\0\u01ca\2\4\0\14\2\16\0"+
    "\5\2\7\0\1\2\1\0\1\2\201\0\5\2\1\0\2\2\2\0"+
    "\4\2\10\0\1\2\1\0\3\2\1\0\1\2\1\0\24\2\1\0"+
    "\123\2\1\0\213\2\10\0\236\2\11\0\46\2\2\0\1\2\7\0"+
    "\47\2\110\0\33\2\5\0\3\2\55\0\53\2\25\0\12\1\4\0"+
    "\2\2\1\0\143\2\1\0\1\2\17\0\2\2\7\0\2\2\12\1"+
    "\3\2\2\0\1\2\20\0\1\2\1\0\36\2\35\0\131\2\13\0"+
    "\1\2\16\0\12\1\41\2\11\0\2\2\4\0\1\2\5\0\26\2"+
    "\4\0\1\2\11\0\1\2\3\0\1\2\27\0\31\2\107\0\1\2"+
    "\1\0\13\2\127\0\66\2\3\0\1\2\22\0\1\2\7\0\12\2"+
    "\4\0\12\1\1\0\7\2\1\0\7\2\5\0\10\2\2\0\2\2"+
    "\2\0\26\2\1\0\7\2\1\0\1\2\3\0\4\2\3\0\1\2"+
    "\20\0\1\2\15\0\2\2\1\0\3\2\4\0\12\1\2\2\23\0"+
    "\6\2\4\0\2\2\2\0\26\2\1\0\7\2\1\0\2\2\1\0"+
    "\2\2\1\0\2\2\37\0\4\2\1\0\1\2\7\0\12\1\2\0"+
    "\3\2\20\0\11\2\1\0\3\2\1\0\26\2\1\0\7\2\1\0"+
    "\2\2\1\0\5\2\3\0\1\2\22\0\1\2\17\0\2\2\4\0"+
    "\12\1\25\0\10\2\2\0\2\2\2\0\26\2\1\0\7\2\1\0"+
    "\2\2\1\0\5\2\3\0\1\2\36\0\2\2\1\0\3\2\4\0"+
    "\12\1\1\0\1\2\21\0\1\2\1\0\6\2\3\0\3\2\1\0"+
    "\4\2\3\0\2\2\1\0\1\2\1\0\2\2\3\0\2\2\3\0"+
    "\3\2\3\0\14\2\26\0\1\2\25\0\12\1\25\0\10\2\1\0"+
    "\3\2\1\0\27\2\1\0\12\2\1\0\5\2\3\0\1\2\32\0"+
    "\2\2\6\0\2\2\4\0\12\1\25\0\10\2\1\0\3\2\1\0"+
    "\27\2\1\0\12\2\1\0\5\2\3\0\1\2\40\0\1\2\1\0"+
    "\2\2\4\0\12\1\1\0\2\2\22\0\10\2\1\0\3\2\1\0"+
    "\51\2\2\0\1\2\20\0\1\2\21\0\2\2\4\0\12\1\12\0"+
    "\6\2\5\0\22\2\3\0\30\2\1\0\11\2\1\0\1\2\2\0"+
    "\7\2\72\0\60\2\1\0\2\2\14\0\7\2\11\0\12\1\47\0"+
    "\2\2\1\0\1\2\2\0\2\2\1\0\1\2\2\0\1\2\6\0"+
    "\4\2\1\0\7\2\1\0\3\2\1\0\1\2\1\0\1\2\2\0"+
    "\2\2\1\0\4\2\1\0\2\2\11\0\1\2\2\0\5\2\1\0"+
    "\1\2\11\0\12\1\2\0\4\2\40\0\1\2\37\0\12\1\26\0"+
    "\10\2\1\0\44\2\33\0\5\2\163\0\53\2\24\0\1\2\12\1"+
    "\6\0\6\2\4\0\4\2\3\0\1\2\3\0\2\2\7\0\3\2"+
    "\4\0\15\2\14\0\1\2\1\0\12\1\6\0\46\2\1\0\1\2"+
    "\5\0\1\2\2\0\53\2\1\0\u014d\2\1\0\4\2\2\0\7\2"+
    "\1\0\1\2\1\0\4\2\2\0\51\2\1\0\4\2\2\0\41\2"+
    "\1\0\4\2\2\0\7\2\1\0\1\2\1\0\4\2\2\0\17\2"+
    "\1\0\71\2\1\0\4\2\2\0\103\2\45\0\20\2\20\0\125\2"+
    "\14\0\u026c\2\2\0\21\2\1\0\32\2\5\0\113\2\25\0\15\2"+
    "\1\0\4\2\16\0\22\2\16\0\22\2\16\0\15\2\1\0\3\2"+
    "\17\0\64\2\43\0\1\2\4\0\1\2\3\0\12\1\46\0\12\1"+
    "\6\0\130\2\10\0\51\2\1\0\1\2\5\0\106\2\12\0\35\2"+
    "\51\0\12\1\36\2\2\0\5\2\13\0\54\2\25\0\7\2\10\0"+
    "\12\1\46\0\27\2\11\0\65\2\53\0\12\1\6\0\12\1\15\0"+
    "\1\2\135\0\57\2\21\0\7\2\4\0\12\1\51\0\36\2\15\0"+
    "\2\2\12\1\54\2\32\0\44\2\34\0\12\1\3\0\3\2\12\1"+
    "\44\2\153\0\4\2\1\0\4\2\3\0\2\2\11\0\300\2\100\0"+
    "\u0116\2\2\0\6\2\2\0\46\2\2\0\6\2\2\0\10\2\1\0"+
    "\1\2\1\0\1\2\1\0\1\2\1\0\37\2\2\0\65\2\1\0"+
    "\7\2\1\0\1\2\3\0\3\2\1\0\7\2\3\0\4\2\2\0"+
    "\6\2\4\0\15\2\5\0\3\2\1\0\7\2\53\0\1\55\11\0"+
    "\1\10\76\0\1\2\15\0\1\2\20\0\15\2\145\0\1\2\4\0"+
    "\1\2\2\0\12\2\1\0\1\2\3\0\5\2\6\0\1\2\1\0"+
    "\1\2\1\0\1\2\1\0\4\2\1\0\13\2\2\0\4\2\5\0"+
    "\5\2\4\0\1\2\64\0\2\2\21\0\1\13\1\11\1\12\1\11"+
    "\u0170\0\1\30\1\31\35\0\1\44\1\45\u01d5\0\1\46\13\0\1\50"+
    "\7\0\1\52\73\0\1\51\45\0\1\54\1\47\2\0\1\53\u01ed\0"+
    "\1\34\1\35\174\0\1\40\1\41\2\0\1\24\1\25\u0197\0\1\26"+
    "\1\27\4\0\1\22\1\23\u0275\0\57\2\1\0\57\2\1\0\205\2"+
    "\6\0\4\2\3\0\2\2\14\0\46\2\1\0\1\2\5\0\1\2"+
    "\2\0\70\2\7\0\1\2\20\0\27\2\11\0\7\2\1\0\7\2"+
    "\1\0\7\2\1\0\7\2\1\0\7\2\1\0\7\2\1\0\7\2"+
    "\1\0\7\2\120\0\1\2\u01d5\0\2\2\15\0\1\32\1\33\1\36"+
    "\1\37\2\0\1\42\1\43\25\0\5\2\5\0\2\2\4\0\126\2"+
    "\6\0\3\2\1\0\132\2\1\0\4\2\5\0\51\2\3\0\136\2"+
    "\21\0\33\2\65\0\20\2\u0200\0\u19b6\2\112\0\u51cd\2\63\0\u048d\2"+
    "\103\0\56\2\2\0\u010d\2\3\0\20\2\12\1\2\2\24\0\57\2"+
    "\20\0\31\2\10\0\106\2\61\0\11\2\2\0\147\2\2\0\4\2"+
    "\1\0\4\2\14\0\13\2\115\0\12\2\1\0\3\2\1\0\4\2"+
    "\1\0\27\2\35\0\64\2\16\0\62\2\34\0\12\1\30\0\6\2"+
    "\3\0\1\2\4\0\12\1\34\2\12\0\27\2\31\0\35\2\7\0"+
    "\57\2\34\0\1\2\12\1\46\0\51\2\27\0\3\2\1\0\10\2"+
    "\4\0\12\1\6\0\27\2\3\0\1\2\5\0\60\2\1\0\1\2"+
    "\3\0\2\2\2\0\5\2\2\0\1\2\1\0\1\2\30\0\3\2"+
    "\2\0\13\2\7\0\3\2\14\0\6\2\2\0\6\2\2\0\6\2"+
    "\11\0\7\2\1\0\7\2\221\0\43\2\15\0\12\1\6\0\u2ba4\2"+
    "\14\0\27\2\4\0\61\2\71\0\1\3\u0502\0\32\4\u1bae\0\u016e\2"+
    "\2\0\152\2\46\0\7\2\14\0\5\2\5\0\1\2\1\0\12\2"+
    "\1\0\15\2\1\0\5\2\1\0\1\2\1\0\2\2\1\0\2\2"+
    "\1\0\154\2\41\0\u016b\2\22\0\100\2\2\0\66\2\50\0\14\2"+
    "\164\0\5\2\1\0\207\2\23\0\12\1\7\0\32\2\6\0\32\2"+
    "\13\0\131\2\3\0\6\2\2\0\6\2\2\0\6\2\2\0\3\2"+
    "\43\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\0\1\1\1\2\1\3\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\10\1\12\1\10\1\13\1\14"+
    "\1\15\2\10\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\12\1\50\1\51\1\52"+
    "\1\51\1\53\1\54\1\10\1\0\1\55\1\0\1\10"+
    "\1\0\1\56";

  private static int [] zzUnpackAction() {
    int [] result = new int[59];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\62\0\144\0\226\0\310\0\372\0\226\0\226"+
    "\0\226\0\u012c\0\u015e\0\u0190\0\u01c2\0\u01f4\0\226\0\226"+
    "\0\226\0\u0226\0\u0258\0\226\0\226\0\226\0\226\0\226"+
    "\0\226\0\226\0\226\0\226\0\226\0\226\0\226\0\226"+
    "\0\u012c\0\226\0\226\0\226\0\226\0\226\0\226\0\226"+
    "\0\226\0\226\0\226\0\u012c\0\u012c\0\226\0\226\0\226"+
    "\0\226\0\u028a\0\226\0\226\0\u02bc\0\u02ee\0\226\0\u0320"+
    "\0\u0352\0\u0384\0\u0226";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[59];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\46\3\1\4\1\5\1\6\2\7\1\10\1\11\5\3"+
    "\1\12\1\13\1\14\1\15\1\12\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\22\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\55\1\4\1\5"+
    "\1\6\1\56\1\57\2\56\1\60\1\61\1\60\1\62"+
    "\1\12\46\3\7\0\5\3\133\0\1\63\61\0\1\64"+
    "\10\0\1\12\3\0\1\12\1\0\3\65\3\22\15\0"+
    "\1\12\12\0\2\12\13\0\1\12\1\0\1\13\61\0"+
    "\2\14\1\66\2\0\3\65\3\22\46\0\4\67\1\14"+
    "\52\67\1\0\2\67\5\0\1\16\3\65\1\0\1\70"+
    "\46\0\1\65\1\12\2\14\1\66\1\12\1\0\3\65"+
    "\3\22\15\0\1\12\12\0\2\12\13\0\2\12\1\71"+
    "\1\14\1\66\1\12\1\0\3\65\3\22\15\0\1\12"+
    "\12\0\2\12\13\0\1\12\57\0\1\60\10\0\3\65"+
    "\1\0\1\70\53\0\1\14\56\0\1\72\61\0\2\14"+
    "\1\66\2\0\3\65\2\22\1\73\61\0\1\65\46\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[950];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\1\1\0\1\1\1\11\2\1\3\11\5\1\3\11"+
    "\2\1\15\11\1\1\12\11\2\1\4\11\1\1\2\11"+
    "\1\1\1\0\1\11\1\0\1\1\1\0\1\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[59];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  /**
   * A special reader that provides line and column numbers,
   * or null if not provided.
   */
  private CztReader reader_ = null;

  /**
   * Records the number of paragraph begins.
   */
  private int begins_ = 0;
  
  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.CIRCUSCONF
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;

  public ContextFreeScanner(Source source)
    throws IOException
  {
    this(source.getReader());
    assert dialect_ != null;
  }

  public ContextFreeScanner(CztReader in)
  {
    this((Reader) in);
    reader_ = in;
    assert dialect_ != null;
  }
  
  /**
   * Low-level LaTeX scanner gets its dialect from the context and propagate to other scanners.
   */
  public Dialect getDialect()
  {
  	return dialect_;
  }
  
  // TODO: perhaps remove? was used by the low-level scanning tags for zstateinfo. yet it's nice piece of info?
   //@SuppressWarnings("unused") // added globally.
   private boolean inSchemaBox_ = false;

  


  private boolean debug_ = false ;
  private long i = 1;
  private static final java.util.Map<Object, String> symbolMap_ = net.sourceforge.czt.parser.util.DebugUtils.getFieldMap(Sym.class);
  private void debug(int type, Object value)
  {
    System.out.println("ContextFreeScanner (" + i + "; " + dialect_.toString() + ") : Sym=" + type + " val=" + symbolMap_.get(type) + " L" + getLine() + " C" + getColumn() + " is " + String.valueOf(value));
    i++;
    System.out.flush();
  }

  /**
   * Creates a new java_cup.runtime.Symbol with line and column
   * information about the current token.
   * The token will have no value.
   */
  private Symbol symbol(int type)
  {
    if (debug_) debug(type, null);
    return new Symbol(type, getLine(), getColumn(), getLocation());
  }

  /**
   * Creates a new java_cup.runtime.Symbol with line and column
   * information about the current token.
   *
   * @param value the value of the Symbol to be returned.
   * @return a new Symbol with column and line information
   *         and the given value.
   */
  private Symbol symbol(int type, Object value)
  {
    if (debug_) debug(type, value);
    return new Symbol(type, getLine(), getColumn(), value);
  }

  private int getLine() 	 
  { 	 
    if (reader_ != null) { 	 
      return reader_.getLine(yychar); 	 
    } 	 
    return yyline; 	 
  } 	 

  private int getColumn() 	 
  { 	 
    if (reader_ != null) { 	 
      return reader_.getColumn(yychar); 	 
    } 	 
    return yycolumn; 	 
  }

  private LocInfo getLocation()
  {
    if (reader_ != null) {
      LocInfo startLoc = reader_.getLocation(yychar);
      LocInfo endLoc = reader_.getLocation(yychar + yytext().length() - 1);
      int start = startLoc.getStart();
      return new LocInfoImpl(getDialect(), startLoc.getSource(),
                             startLoc.getLine(),
                             startLoc.getColumn(),
                             start,
                             endLoc.getStart() + endLoc.getLength() - start);
    }
    return new LocInfoImpl(getDialect(), null, yyline, yycolumn, yychar, yytext().length());
  }

  private void beginZed()
  {
    if (begins_ > 0) {
      String msg = "Unexpected begin of paragraph; " +
        "end of paragraph expected instead for " + dialect_.toString() + " in ContextFreeScanner ";
      throw new ScanException(dialect_, msg, getLocation());
    }
    begins_++;
    yybegin(Z);
  }

  private void endZed()
  {
    begins_--;
    assert begins_ >= 0;
    if (begins_ == 0) {
      yybegin(YYINITIAL);
    }
  }

/* NEW-DESIGN: refactoring current VCG to have a multi-layered / better interface using meta-level schemas.

  // yytext() might contain {IGNORE}* tokens when starting with ZSTATE strings...?
  // put the longer strings first to avoid catching the smaller ones too early (e.g., retrievein before retrieve)
  private int retrieveZState(String yyt)
  {
    if (!inSchemaBox_)
      throw new ScanException(dialect_, "Cannot define Z state or refinement relationships outside an SCHBOX for " 
      + dialect_.toString() + " in ContextFreeScanner", getLocation());
    //yyt = yyt.trim();
    if (yyt.startsWith(ZString.ZSTATE))
      return Sym.ZSTATE;
    else if (yyt.startsWith(ZString.ZSTINIT))
      return Sym.ZSTINIT;
    else if (yyt.startsWith(ZString.ZSTFIN))
      return Sym.ZSTFIN;
    else if (yyt.startsWith(ZString.ZASTATE))
      return Sym.ZASTATE;
    else if (yyt.startsWith(ZString.ZCSTATE))
      return Sym.ZCSTATE;
    else if (yyt.startsWith(ZString.ZAINITIN))
      return Sym.ZAINITIN;
    else if (yyt.startsWith(ZString.ZAFINOUT))
      return Sym.ZAFINOUT;
    else if (yyt.startsWith(ZString.ZASTINIT))
      return Sym.ZASTINIT;
    else if (yyt.startsWith(ZString.ZCSTINIT))
      return Sym.ZCSTINIT;
    else if (yyt.startsWith(ZString.ZASTFIN))
      return Sym.ZASTFIN;
    else if (yyt.startsWith(ZString.ZCINITIN))
      return Sym.ZCINITIN;
    else if (yyt.startsWith(ZString.ZCFINOUT))
      return Sym.ZCFINOUT;
    else if (yyt.startsWith(ZString.ZCSTFIN))
      return Sym.ZCSTFIN;
    else if (yyt.startsWith(ZString.ZRETRIEVEIN))
      return Sym.ZRETRIEVEIN;
    else if (yyt.startsWith(ZString.ZRETRIEVEOUT))
      return Sym.ZRETRIEVEOUT;
    else if (yyt.startsWith(ZString.ZRETRIEVE))
      return Sym.ZRETRIEVE;
    else if (yyt.startsWith(ZString.ZFSREFINES))
      return Sym.ZFSREFINES;
    else if (yyt.startsWith(ZString.ZBSREFINES))
      return Sym.ZBSREFINES;
    else
      throw new ScanException(dialect_, "Unknown ZState " + yyt + 
      	" for " + dialect_.toString() + " in ContextFreeScanner ", getLocation());
  }
*/


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ContextFreeScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ContextFreeScanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1762) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 42: 
          { //System.out.println("SPACE-IGNORE");
          }
        case 47: break;
        case 36: 
          { return symbol(Sym.LINTER);
          }
        case 48: break;
        case 33: 
          { return symbol(Sym.RCIRCRENAME);
          }
        case 49: break;
        case 28: 
          { return symbol(Sym.LCIRCGUARD);
          }
        case 50: break;
        case 40: 
          { endZed(); inSchemaBox_ = false; return symbol(Sym.END);
          }
        case 51: break;
        case 38: 
          { return symbol(Sym.LCIRCCONF);
          }
        case 52: break;
        case 6: 
          { beginZed(); return symbol(Sym.CIRCUS);
          }
        case 53: break;
        case 17: 
          { return symbol(Sym.RSQUARE);
          }
        case 54: break;
        case 29: 
          { return symbol(Sym.RCIRCGUARD);
          }
        case 55: break;
        case 10: 
          { String msg = "Unexpected character "+ yytext() + "(" +
                     "\\u" + Integer.toString(yytext().charAt(0), 16) +
                     ") inside para for " + dialect_.toString() + " in ContextFreeScanner.";
                   throw new ScanException(dialect_, msg, getLocation());
          }
        case 56: break;
        case 30: 
          { return symbol(Sym.LSCHEXPRACT);
          }
        case 57: break;
        case 14: 
          { return symbol(Sym.LPAREN);
          }
        case 58: break;
        case 11: 
          { return symbol(Sym.INSTROKE);
          }
        case 59: break;
        case 41: 
          { return symbol(Sym.NL);
          }
        case 60: break;
        case 21: 
          { return symbol(Sym.RBIND);
          }
        case 61: break;
        case 19: 
          { return symbol(Sym.RBRACE);
          }
        case 62: break;
        case 20: 
          { return symbol(Sym.LBIND);
          }
        case 63: break;
        case 44: 
          { beginZed(); inSchemaBox_ = true; return symbol(Sym.GENSCH);
          }
        case 64: break;
        case 1: 
          { LocString value = new LocString(yytext(), getLocation());
                   return symbol(Sym.TEXT, value);
          }
        case 65: break;
        case 22: 
          { return symbol(Sym.LDATA);
          }
        case 66: break;
        case 23: 
          { return symbol(Sym.RDATA);
          }
        case 67: break;
        case 3: 
          { beginZed(); return symbol(Sym.AX);
          }
        case 68: break;
        case 9: 
          { return symbol(Sym.NUMERAL,
                                 new LocInt(yytext(), getLocation()));
          }
        case 69: break;
        case 34: 
          { return symbol(Sym.LPAR);
          }
        case 70: break;
        case 37: 
          { return symbol(Sym.RINTER);
          }
        case 71: break;
        case 31: 
          { return symbol(Sym.RSCHEXPRACT);
          }
        case 72: break;
        case 8: 
          { Symbol sym = symbol(
                                 Sym.DECORWORD,
                                 new Decorword(yytext(), getLocation()));
                   //System.out.println("DECORWORD= '" + yytext() );
                   
                   return sym;
          }
        case 73: break;
        case 24: 
          { return symbol(Sym.LCIRCCHANSET);
          }
        case 74: break;
        case 7: 
          { beginZed(); return symbol(Sym.CIRCUSACTION);
          }
        case 75: break;
        case 35: 
          { return symbol(Sym.RPAR);
          }
        case 76: break;
        case 5: 
          { String msg = "Unexpected character "+ yytext() + "(" +
                     "\\u" + Integer.toString(yytext().charAt(0), 16) +
                     ") outside para for " + dialect_.toString() + " in ContextFreeScanner.";
                   throw new ScanException(dialect_, msg, getLocation());
          }
        case 77: break;
        case 45: 
          { StringBuilder msg = new StringBuilder("Unexpected character "+ yytext() + "(");
                   for (int i = 0; i < yytext().length(); i++) {
                     msg.append("\\u"); 
                     msg.append(Integer.toString(yytext().charAt(i), 16));
                   }
                   msg.append(") for ");
                   msg.append(dialect_.toString());
                   msg.append(" in ContextFreeScanner.");
                   throw new ScanException(dialect_, msg.toString(), getLocation());
          }
        case 78: break;
        case 43: 
          { beginZed(); return symbol(Sym.GENAX);
          }
        case 79: break;
        case 12: 
          { return symbol(Sym.OUTSTROKE);
          }
        case 80: break;
        case 4: 
          { beginZed(); inSchemaBox_ = true; return symbol(Sym.SCH);
          }
        case 81: break;
        case 16: 
          { return symbol(Sym.LSQUARE);
          }
        case 82: break;
        case 39: 
          { return symbol(Sym.RCIRCCONF);
          }
        case 83: break;
        case 2: 
          { beginZed(); return symbol(Sym.ZED);
          }
        case 84: break;
        case 32: 
          { return symbol(Sym.LCIRCRENAME);
          }
        case 85: break;
        case 15: 
          { return symbol(Sym.RPAREN);
          }
        case 86: break;
        case 13: 
          { return symbol(Sym.NEXTSTROKE);
          }
        case 87: break;
        case 27: 
          { return symbol(Sym.CIRCRINST);
          }
        case 88: break;
        case 26: 
          { return symbol(Sym.CIRCLINST);
          }
        case 89: break;
        case 25: 
          { return symbol(Sym.RCIRCCHANSET);
          }
        case 90: break;
        case 18: 
          { return symbol(Sym.LBRACE);
          }
        case 91: break;
        case 46: 
          { LocInt locInt =
                     new LocInt(yytext().substring(1,2),getLocation());
                   return symbol(Sym.NUMSTROKE, locInt);
          }
        case 92: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {   return  new java_cup.runtime.Symbol(Sym.EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
