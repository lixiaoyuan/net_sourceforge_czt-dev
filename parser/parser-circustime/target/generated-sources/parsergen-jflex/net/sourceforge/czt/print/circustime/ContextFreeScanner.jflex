
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
package net.sourceforge.czt.print.circustime;

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
%%
/* -----------------Options and Declarations Section----------------- */
%class ContextFreeScanner
%public
%unicode
%line
%column
%char
%cupsym Sym
//%cup
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol
%eofval{
  return  new java_cup.runtime.Symbol(Sym.EOF);
%eofval}
%eofclose

%{
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
  						Dialect.Z
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
%}
   
/***********************************************************************
  Z characters (6.2)
 ***********************************************************************/

/* TODO: Distinguish between DIGIT and DECIMAL */
DIGIT = {DECIMAL}
DECIMAL = [:digit:]

/* TODO: What about OTHERLETTER? */
LETTER = [:letter:] | \uD835 [\uDD38-\uDD51]

PUNCT =   "\u002C" /* comma */
        | "\u002E" /* full stop */
        | "\u003A" /* colon */
        | "\u003B" /* semicolon */

SPECIAL =   {STROKECHAR}
          | {WORDGLUE}
          | {BRACKET}
          | {BOXCHAR}
          | {NLCHAR}
          | {SPACE}
          | {CONTROL}

/* NOT_SYMBOL ist only needed to define SYMBOL */
NOT_SYMBOL = {DIGIT} | {LETTER} | {SPECIAL} | {PUNCT}

/* SYMBOL are all the characters that are not NOT_SYMBOL
   nor a special Unicode symbol */
SYMBOL = !(![^] | {NOT_SYMBOL} | "\uD835")

/* SPECIAL
   ======= */

/* Stroke */
STROKECHAR = {INSTROKE} | {OUTSTROKE} | {NEXTSTROKE}
INSTROKE = "\u003F"   /* question mark */
OUTSTROKE = "\u0021"  /* exclamation mark */
NEXTSTROKE = "\u2032" /* prime */

/* Word glue (6.4.4.2) */
WORDGLUE = {NE} | {SE} | {SW} | {NW} | {LL}
NE = "\u2197" /* north east arrow */
SW = "\u2199" /* south west arrow */
SE = "\u2198" /* south east arrow */
NW = "\u2196" /* north west arrow */
LL = "\u005F" /* low line */

/* Bracket characters (6.4.4.3) */
BRACKET = {LPAREN} | {RPAREN} | {LSQUARE} | {RSQUARE} | {LBRACE} | {RBRACE} | {LBIND} | 
          {RBIND} | {LDATA} | {RDATA}
 
          | {LCIRCCHANSET} | {RCIRCCHANSET} | {CIRCLINST} | {CIRCLINST} 
          | {LCIRCGUARD} | {RCIRCGUARD} | {LSCHEXPRACT} | {RSCHEXPRACT}
          | {LCIRCRENAME} | {RCIRCRENAME} | {LPAR} | {RPAR} | {LINTER} 
          | {RINTER}

 /* Support for Circus Time */
          | {LCIRCTIME} | {RCIRCTIME}
 
LPAREN = "\u0028"  /* left parenthesis */
RPAREN = "\u0029"  /* right parenthesis */
LSQUARE = "\u005B" /* left square bracket */
RSQUARE = "\u005D" /* right square bracket */
LBRACE = "\u007B"  /* left curly bracket */
RBRACE = "\u007D"  /* right curly bracket */
LBIND = "\u2989"   /* Z notation left binding bracket */
RBIND = "\u298A"   /* Z notation right binding bracket */
LDATA = "\u27EA"   /* mathmatical left double angle bracket */
RDATA = "\u27EB"   /* mathmatical right double angle bracket */

LCIRCCHANSET = "\u2983" /* channel set left bracket */
RCIRCCHANSET = "\u2984" /* channel set right bracket */
CIRCLINST = "\u230A" /* left indexed process instantiation angle bracket */
CIRCRINST = "\u230B" /* left indexed process instantiation angle bracket */
LCIRCGUARD = "\u3014" /* left action guard bracket */
RCIRCGUARD = "\u3015" /* right action guard bracket */
LSCHEXPRACT = "\u2768" /* left action guard bracket */
RSCHEXPRACT = "\u2769" /* right action guard bracket */
LCIRCRENAME = "\u3016" /* left process renaming bracket */
RCIRCRENAME = "\u3017" /* right process renaming bracket */
LPAR = "\u27E6" /* left parallel bracket */
RPAR = "\u27E7" /* right parallel bracket */
LINTER = "\u301A"   /* left interleave bracket */
RINTER = "\u301B"   /* left interleave bracket */

/* Support for Circus Time */
LCIRCTIME = "\u27EC" /* left time bracket */
RCIRCTIME = "\u27ED" /* right time bracket */


/* Box characters (6.4.4.3) */
BOXCHAR = {ZEDCHAR} | {AXCHAR} | {SCHCHAR} | {GENCHAR} | {ENDCHAR}
     | {CIRCUSCHAR} | {CIRCUSACTIONCHAR} 
ZEDCHAR = "\u2500" /* box drawings light horizontal */
AXCHAR = "\u2577"  /* box drawings light down */
SCHCHAR = "\u250C" /* box drawings light down and right */
GENCHAR = "\u2550" /* box drawings double horizontal */
ENDCHAR = "\u2514" /* box drawings light up and right */

CIRCUSCHAR = "\u257A"       
CIRCUSACTIONCHAR = "\u2576"  



/* Other SPECIAL characters (6.4.4.5) */
NLCHAR = "\u2028" | {CR} {LF} | {CR} | {LF}  /* line separator TODO add BEF*/
SPACE =   "\u0020" /* space */

CONTROL = {TAB}
LF = "\n"
CR = "\r"
TAB = "\t"

NOT_BOXCHAR = !(![^] | {BOXCHAR})
TEXT = {NOT_BOXCHAR}*

/***********************************************************************
  Lexis (7)
 ***********************************************************************/


DECORWORD = {WORD} {STROKE}*
WORD =   {WORDPART}+
       | {LETTER} {ALPHASTR} {WORDPART}*
       | {SYMBOL}+ {WORDPART}*
       | {PUNCT}+ ("\u003D" /* equals sign */)?
WORDPART = {WORDGLUE} ( {ALPHASTR} | {SYMBOL}* )
ALPHASTR = ({LETTER} | {DIGIT} )*
NUMERAL = {DIGIT}+
STROKE = {STROKECHAR} | {SE} {DIGIT} {NW}
ZED = {ZEDCHAR}
AX = {AXCHAR}
SCH = {SCHCHAR}
GENAX = {AXCHAR} {GENCHAR}
GENSCH = {SCHCHAR} {GENCHAR}
END = {ENDCHAR}
NL = {NLCHAR}

CIRCUS = {CIRCUSCHAR}
CIRCUSACTION = {CIRCUSACTIONCHAR}


/*

//NL_SPACE = ({NL}|{SPACE})
ZREFIO = //{NL_SPACE}*
                     ("zretrievein" | "zretrieveout" |
                      "zainitin" | "zafinout" | "zcinitin" |
                      "zcfinout" )
         //{NL_SPACE}+

ZSTATE = //{NL_SPACE}*
              ("zstate" | "zstinit" | "zstfin" |
               "zastate" | "zcstate" |
               "zastinit" | "zcstinit" | "zretrieve" |
               "zastfin" | "zcstfin")
          // {NL_SPACE}+

ZREFINES = //{NL_SPACE}*
            ("zfsrefines" | "zbsrefines")
*/


%state Z

%%
/* ------------------------Lexical Rules Section---------------------- */

  {ZED}         {  beginZed(); return symbol(Sym.ZED); }
  {AX}          {  beginZed(); return symbol(Sym.AX); }
  {GENAX}       {  beginZed(); return symbol(Sym.GENAX); }
  {SCH}         {  beginZed(); inSchemaBox_ = true; return symbol(Sym.SCH); }
  {GENSCH}      {  beginZed(); inSchemaBox_ = true; return symbol(Sym.GENSCH); }
                
<YYINITIAL> {
  
  /* Why aren't these productions outside YYINITIAL or those outside not inside? */
  {CIRCUS}            {  beginZed(); return symbol(Sym.CIRCUS); }   
  {CIRCUSACTION}      {  beginZed(); return symbol(Sym.CIRCUSACTION); }   
  

  {TEXT}        {
                   LocString value = new LocString(yytext(), getLocation());
                   return symbol(Sym.TEXT, value);
                }
  .             {
                   String msg = "Unexpected character "+ yytext() + "(" +
                     "\\u" + Integer.toString(yytext().charAt(0), 16) +
                     ") outside para for " + dialect_.toString() + " in ContextFreeScanner.";
                   throw new ScanException(dialect_, msg, getLocation());
                }

}

<Z> {

  "section"     { return symbol(Sym.SECTION); }
  "parents"     { return symbol(Sym.PARENTS); }
  {NL} {SPACE}* "|" {SPACE}* {NL}
                { return symbol(Sym.WHERE); }

  /* Boxes */
  {END}         {  endZed(); inSchemaBox_ = false; return symbol(Sym.END); }
  {NL}          {  return symbol(Sym.NL); }

//  // IO must appear first to match zretrievein earlier (e.g., zretrieve in ZSTATE it smaller).
//  {ZREFIO}      { return symbol(retrieveZState(yytext())); }
//  {ZSTATE}      { return symbol(retrieveZState(yytext())); }
//  {ZREFINES}    { return symbol(retrieveZState(yytext())); }

  

  /* strip spaces (context-sensitive lexis; 7.4.1)
     \t is added so that unicode files containing tabs
     can be read properly */
  {SPACE} | {CONTROL}
                {
                  //System.out.println("SPACE-IGNORE");
                }

  

  /* Brackets */
  {LPAREN}      {  return symbol(Sym.LPAREN); }
  {RPAREN}      {  return symbol(Sym.RPAREN); }
  {LSQUARE}     {  return symbol(Sym.LSQUARE); }
  {RSQUARE}     {  return symbol(Sym.RSQUARE); }
  {LBRACE}      {  return symbol(Sym.LBRACE); }
  {RBRACE}      {  return symbol(Sym.RBRACE); }
  {LBIND}       {  return symbol(Sym.LBIND); }
  {RBIND}       {  return symbol(Sym.RBIND); }
  {LDATA}       {  return symbol(Sym.LDATA); }
  {RDATA}       {  return symbol(Sym.RDATA); }
  
  {LCIRCCHANSET} {  return symbol(Sym.LCIRCCHANSET); }
  {RCIRCCHANSET} {  return symbol(Sym.RCIRCCHANSET); }  
  {CIRCLINST}    {  return symbol(Sym.CIRCLINST); }  
  {CIRCRINST}    {  return symbol(Sym.CIRCRINST); }  
  {LCIRCGUARD}   {  return symbol(Sym.LCIRCGUARD); }
  {RCIRCGUARD}   {  return symbol(Sym.RCIRCGUARD); }
  {LSCHEXPRACT}  {  return symbol(Sym.LSCHEXPRACT); }
  {RSCHEXPRACT}  {  return symbol(Sym.RSCHEXPRACT); }
  {LCIRCRENAME}  {  return symbol(Sym.LCIRCRENAME); }
  {RCIRCRENAME}  {  return symbol(Sym.RCIRCRENAME); }
  {LPAR}         {  return symbol(Sym.LPAR); }  
  {RPAR}         {  return symbol(Sym.RPAR); }  
  {LINTER}       {  return symbol(Sym.LINTER); }  
  {RINTER}       {  return symbol(Sym.RINTER); }
  
  /* Support for Circus Time */
  {LCIRCTIME} {  return symbol(Sym.LCIRCTIME); }
  {RCIRCTIME} {  return symbol(Sym.RCIRCTIME); }
  
  {INSTROKE}    {  return symbol(Sym.INSTROKE); }
  {OUTSTROKE}   {  return symbol(Sym.OUTSTROKE); }
  {NEXTSTROKE}  {  return symbol(Sym.NEXTSTROKE); }
  {SE} {DIGIT} {NW}
                {
                   LocInt locInt =
                     new LocInt(yytext().substring(1,2),getLocation());
                   return symbol(Sym.NUMSTROKE, locInt);
                }
  {NUMERAL}     {  return symbol(Sym.NUMERAL,
                                 new LocInt(yytext(), getLocation())); }

  
  
  {DECORWORD}   {
                   

                   Symbol sym = symbol(
                                 Sym.DECORWORD,
                                 new Decorword(yytext(), getLocation()));
                   //System.out.println("DECORWORD= '" + yytext() );
                   
                   return sym; }

  /* error fallback */
  "\ud835" .    {
                   StringBuilder msg = new StringBuilder("Unexpected character "+ yytext() + "(");
                   for (int i = 0; i < yytext().length(); i++) {
                     msg.append("\\u"); 
                     msg.append(Integer.toString(yytext().charAt(i), 16));
                   }
                   msg.append(") for ");
                   msg.append(dialect_.toString());
                   msg.append(" in ContextFreeScanner.");
                   throw new ScanException(dialect_, msg.toString(), getLocation());
                }
  .             {
                   String msg = "Unexpected character "+ yytext() + "(" +
                     "\\u" + Integer.toString(yytext().charAt(0), 16) +
                     ") inside para for " + dialect_.toString() + " in ContextFreeScanner.";
                   throw new ScanException(dialect_, msg, getLocation());
                }
}

