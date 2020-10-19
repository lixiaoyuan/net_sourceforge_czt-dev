
/* --------------------------Usercode Section------------------------ */
package net.sourceforge.czt.parser.zeves;



import java.io.IOException;
import java.util.Properties;
import java.util.Stack;

import java_cup.runtime.Symbol;
import net.sourceforge.czt.parser.util.ErrorType;
import net.sourceforge.czt.parser.util.LatexCommand;
import net.sourceforge.czt.parser.util.LatexMarkupFunction;
import net.sourceforge.czt.parser.util.LatexSym;
import net.sourceforge.czt.parser.util.LocInfo;
import net.sourceforge.czt.parser.util.LocInfoImpl;
import net.sourceforge.czt.parser.util.LocToken;
import net.sourceforge.czt.parser.util.LocTokenImpl;
import net.sourceforge.czt.parser.util.MarkupDirective;
import net.sourceforge.czt.parser.util.ParsePropertiesKeys;
import net.sourceforge.czt.parser.util.ScanException;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.util.CztLogger;
import net.sourceforge.czt.z.util.ZString;
//import java.util.logging.Level;

import net.sourceforge.czt.parser.z.ZParseError;
import net.sourceforge.czt.parser.z.ZParseMessage;


import net.sourceforge.czt.zeves.util.ZEvesString;



/**
 * <p>
*   The low-level latex to unicode scanner for Z.
 *   See {@link LatexToUnicode} for a high level latex to unicode converter.
 * </p>
 * <p>
 *   This is a JFlex generated scanner for translating
 *   Z
 *   specifications written in the latex mark-up language into unicode.
 *   It provides tokens (instances of class {@link Symbol}) whose values
 *   are unicode strings and which contain line and column number
 *   information from the original latex file or stream.
 *   See {@link LatexSym} for a list of possible token kinds.
 * </p>
 * <p>
 *   In order to work properly, a map containing the latex mark-up function
 *   of the current section to be scanned is needed.  This map must be
 *   updated when a new section header is recognised.
 *   The {@link LatexMarkupParser} is
 *   responsible for this task, and should process the output of an instance
 *   of this class before it can be processed further.
 * </p>
 * <p>
 * Limitations:
 * </p>
 * <ul>
 *   <li>
 *     The name of a generic schema definition should not contain
 *     nested braces.
 *   </li>
 *   <li>
 *     Latex markup directives are recognised only outside of a
 *     formal paragraph and only after they have been defined.
 *   </li>
 * </ul>
 */
@SuppressWarnings("unused") 
%%

/* -----------------Options and Declarations Section----------------- */

%class Latex2Unicode
%public
%unicode
%line
%column
%char
%implements ParsePropertiesKeys
%implements LatexMarkupParser.LatexLexer
%function next
%type LocToken

%{
  /**
   * <p>
   * The latex markup function for the current section to be scanned.
   * It must be kept up to date by another class that recognises
   * section headers.
   * </p>
   */
  private LatexMarkupFunction latexCommands_ = null;



  /**
   * A stack of BraceType.
   * Each "{"-token pushs a BraceType on the stack,
   * each "}"-token pops a BraceType from the stack.
   *
   * A "^"-token followed by "{"-token pushs
   * a <code>BraceType.SUPER</code>
   * (no space to be inserted after the scripts)
   * or <code>BraceType.SUPER_SPACE</code>
   * (space has to be inserted after
   * the scripts) on the stack.  A "_"-token followed by "{"-token pushs
   * a <code>BraceType.SUB</code>
   * (no space to be inserted after the scripts)
   * or <code>BraceType.SUB_SPACE</code>
   * (space has to be inserted after
   * the scripts) on the stack.
   * The "{"-token that encloses a schema name
   * pushes a  <code>BraceType.NAME</code>
   * (space has to be inserted after closing brace) on the stack.
   * All other "{"-token just push a
   * <code>BraceType.BRACE</code> on the stack.
   */
  private Stack<BraceType> braceStack_ = new Stack<BraceType>();

  /**
   * A boolean indicating whether a space has to inserted after all
   * following subscripts and superscripts.
   */
  private boolean addSpace_ = false;

  private boolean catchingThmName_ = false;

  /**
   * True iff we are scanning a directive.
   */
  private boolean directive_ = false;

  /**
   * True iff within a axdef or gendef definition
   * It can be used to mark labelled predicates.
   */
   
  private boolean inAxiomaticBox_ = false;

   
  private boolean inSchemaBox_ = false;

  
  /**
   * A boolean indicating whether we are within a zproof script or not.
   */
  private boolean inProofScript_ = false;

  /**
   * Apply commands are complex:
   *    a) APPLY thm-name ";"
   *    b) APPLY thm-name TO EXPRESSION/PREDICATE expr/pred ";"
   *
   * We add a small (4) state(s) machine to control adding spaces and making
   * the inner words as Proof words rather than Unicode.
   *
   * a) (-1) "apply" (0) name (1) ";"  (-1)
   * b) (-1) "apply" (0) name (1) "to" (2) "expression" (-1)
   *
   * -1 = not within apply cmd yet
   *  0 = just seen "apply" kw; wait for name
   *  1 = just seen thm-name; wait for ";" and reset or wait "to"
   *  2 = just seen "to"; wait for "expression" and reset
   *
   * where "name" lexis as the (LETTER | DIGIT)* group. We addSpace_=true
   * just before (1) and (2); just after "expression" in state (2).
   */
  //private int NOT_WITHIN_APPLY = -1;
  //private int JUST_AFTER_APPLY =  0;
  //private int JUST_AFTER_THMNM =  1;
  //private int JUST_AFTER_TO    =  2;
  //private int applyState_      = NOT_WITHIN_APPLY;

  private LocToken synDefOpName_ = null;

  

  /**
   * The source to be scanned.
   */
  private Source source_ = null;

  private SectionInfo sectInfo_;

  private Properties properties_;

  /**
   * A stack of open \begin{something} commands.
   * The stack just contains the string inside the braces
   * ("something" in the example above).
   * This information is only used to check whether every \begin command
   * has a corresponding \end command (making it possible to
   * provide an error message and warning if not). It is not needed
   * by the scanner itself.
   */
  private final Stack<String> openBeginCommands_ = new Stack<String>();

  /**
   * Name of file from which source text originated.
   */
  private String filename_;

  /** 
   * Line number given in most recent %%Zloc directive.
   */
  private int locLine = 1;   

  /** 
   * Column number given in most recent %%Zloc directive.
   */
  private int locColumn = 1;

  /** 
   * Character number given in most recent %%Zloc directive.
   */
  private int locChar = 0;

  /**
   * Line number in source text of line following most recent %%Zloc directive.
   */
  private int locyyline = 1;

  /**
   * Character number in source text of character following most recent %%Zloc directive.
   */
  private int locyychar = 0;

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  private final Dialect dialect_ = 
  						Dialect.ZEVES
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;

  /**
   * Creates a new scanner for the source.
   */
  public Latex2Unicode(Source source, SectionInfo sectInfo, Properties properties)
    throws IOException
  {
    this(source.getReader());
    if (sectInfo == null) throw new NullPointerException();
    source_ = source;
    sectInfo_ = sectInfo;
    properties_ = properties;
    filename_ = source.toString();
    assert dialect_ !=null;
    if (!dialect_.equals(sectInfo.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in Latex2Unicode scanner. " + dialect_.toString() +
    		" expected, but section manager dialect " + sectInfo.getDialect() + " found.");
  }
  
  /**
   * Low-level LaTeX scanner gets its dialect from the context and propagate to other scanners.
   */
  @Override
  public Dialect getDialect()
  {
  	assert dialect_.equals(sectInfo_.getDialect()) : "dialect = " + 
  				dialect_ + "; sectInfo.dialect = " + sectInfo_.getDialect();
  	return sectInfo_.getDialect();
  }

  public Source getSource()
  {
    return source_;
  }

  private String getLoc()
  {
    return filename_;
  }

  private LocInfo getLocation()
  {
    final int lineNo = locLine + yyline - locyyline;
    final int columnNo = yyline == locLine? locColumn + yycolumn : yycolumn;
    final int charNo = locChar + yychar - locyychar;
    return new LocInfoImpl(getDialect(), 
    											 getLoc(),
                           lineNo, columnNo,
                           charNo, yytext().length());
  }

  private boolean getBooleanProperty(String propertyKey)
  {
    if (properties_ == null) {
      return false;
    }
    return "true".equals(properties_.getProperty(propertyKey));
  }

  private LocToken result(String spelling)
    throws IOException
  {
    return result(LatexSym.UNICODE, spelling);
  }

  private LocToken result(LatexSym sym)
    throws IOException
  {
    return result(sym, yytext());
  }
  
  protected SectionInfo getSectionInfo()
  {
  	return sectInfo_;
  }

  private boolean debug_ = false;
  private long i = 1;
  private void debug(LatexSym sym, String spelling)
  {
      System.out.println("Latex2Unicode (" + i + "; "+ getDialect() +")  : " + spelling + "\t " + sym); i++;
      System.out.flush();
  }

  private LocToken result(LatexSym sym, String spelling)
    throws IOException
  {
    if (debug_) debug(sym, spelling);
    return new LocTokenImpl(sym, spelling, getLocation());
  }

  /**
   * Writes a space to the output and sets
   * <code>addSpace_</code> to <code>false</code>
   * if <code>addSpace_</code> is <code>true</code>.
   * Does nothing if <code>addSpace_</code> is
   * <code>false</code>.
   */
  private String addSpace()
    throws IOException
  {
    if (addSpace_) {
      addSpace_ = false;
      return ZString.SPACE;
    }
    return "";
  }

  /**
   * Returns a north east arrow if <code>string</code>
   * equals "^" and a south east arrow if
   * <code>string</code> equalas "_".
   * Throws an IllegalArgumentException for all other strings.
   */
  private String beginScript(String string)
  {
    if ("^".equals(string)) return ZString.NE;
    if ("_".equals(string)) return ZString.SE;
    throw new IllegalArgumentException("Invalid start LaTeX sub/super script character " + string + 
    	" for " + dialect_.toString() + " in Latex2Unicode parser.");
  }

  /**
   * Returns a south west arrow if <code>string</code>
   * equals "^" and a north west arrow if
   * <code>string</code> equalas "_".
   * Throws an IllegalArgumentException for all other strings.
   */
  private String endScript(String string)
  {
    if ("^".equals(string)) return ZString.SW;
    if ("_".equals(string)) return ZString.NW;
    throw new IllegalArgumentException("Invalid end LaTeX sub/super script character " + string + 
    	" for " + dialect_.toString() + " in Latex2Unicode parser.");
  }

  /**
   * Returns the latex mark-up function.
   * @see #setMarkupFunction
   */
  public LatexMarkupFunction getMarkupFunction()
  {
    return latexCommands_;
  }

  /**
   * Sets the latex mark-up function.  This is a mapping
   * from string (representing a latex command)
   * to {@link LatexCommand} containing the unicode representation.
   *
   * This map must be kept up to date to ensure proper working of
   * instances of this class.
   */
  public void setMarkupFunction(LatexMarkupFunction markupFunction)
  {
    latexCommands_ = markupFunction;
  }

  /**
   * Returns an unicode string representation of the given latex
   * command.
   *
   * @param latexCommand the latex command.
   * @param spaces indicates whether spaces should be added or not.
   * @return the unicode represention of the given latex command,
   *          or null if the command cannot be found.  Spaces are
   *          added (depending on the type of the command) if spaces
   *          is true.
   */
  private String toUnicode(String latexCommand, boolean spaces)
  {
    if (latexCommands_ == null) {
    	CztLogger.getLogger(Latex2Unicode.class).severe("Null latexCommands_ map! This should never happen, returning null." +
    		"Cannot tanslate to Unicode: " 
	    	+ latexCommand + " for " + dialect_.toString() + " in Latex2Unicode scanner.");
      return null;
    }
    MarkupDirective directive = (MarkupDirective)
      latexCommands_.getCommandDirective(latexCommand);
    if (directive == null) {
      return null;
    }
    String result = directive.getUnicode();
    if (spaces) {
      if (directive.addLeftSpace()) result = ZString.SPACE + result;
      if (directive.addRightSpace()) addSpace_ = true;
    }
    return result;
  }

  private void reportError(ErrorType type, ZParseMessage msg, String arg0)
  {
    reportError(type, msg, arg0, null);
  }

  private void reportError(ErrorType type, ZParseMessage msg,
                           String arg0, String info)
  {
    // TODO: what's this? To be used somewhere?
    //final Level level =
    //  ErrorType.ERROR.equals(type) ? Level.SEVERE : Level.WARNING;
    final LocInfo locInfo = getLocation();
    final Object[] args = { arg0, locInfo };
    ZParseError.report(sectInfo_, getSource(), type, msg, args, locInfo, info);
  }
  
  
  private void reportError(ErrorType type, ZEvesParseMessage msg,
                           String arg0, String info)
  {
    final LocInfo locInfo = getLocation();
    final Object[] args = { arg0, locInfo };
    ZEvesParseError.report(sectInfo_, getSource(), type, msg, args, locInfo, info);
  }
  

  private void reportUnknownLatexCommand(String command)
  {
    ErrorType errorType = ErrorType.ERROR;
    if (getBooleanProperty(PROP_IGNORE_UNKNOWN_LATEX_COMMANDS)) {
      errorType = ErrorType.WARNING;
    }
    String info = null;
    if (isFuzzCommand(command)) {
      info = "Make sure your specification is ISO Standard Z conforming "
        + "or try to add fuzz_toolkit as a parent to get access to some "
        + "commonly used non-Standard latex commands.";
    }
    reportError(errorType,
                ZParseMessage.MSG_UNKNOWN_LATEX_COMMAND,
                command,
                info);
  }

  private boolean isFuzzCommand(String command)
  {
    if (sectInfo_ == null)
    {
    	CztLogger.getLogger(Latex2Unicode.class).severe("Null section infor object for checking Fuzz command " + command
    		+ " for dialect " + dialect_.toString() + ". Returning false.");
      return false;
    }
    else
    {
	    try {
	      Key<LatexMarkupFunction> key = new Key<LatexMarkupFunction>(
	        net.sourceforge.czt.util.Section.FUZZ_TOOLKIT.getName(), LatexMarkupFunction.class);
	      LatexMarkupFunction lmf = sectInfo_.get(key);
	      if (lmf.getCommandDirective(command) != null) return true;
	    }
	    catch (CommandException e) {
	    }
	    return false;
	  }
  }

  private void begin(String name)
  {
    openBeginCommands_.push(name);
  }

  private void end(String name)
  {
    if (openBeginCommands_.empty()) {
      reportError(ErrorType.ERROR,
                  ZParseMessage.MSG_UNMATCHED_BEGIN_END,
                  name);
    }
    else {
      String openCommand = openBeginCommands_.pop();
      if (! name.equals(openCommand)) {
        reportError(ErrorType.ERROR,
                    ZParseMessage.MSG_UNMATCHED_BEGIN_END,
                    openCommand);
      }
    }
  }

/*
  // yytext() might contain {IGNORE}* tokens when starting with ZSTATE strings...
  // put the longer strings first to avoid catching the smaller ones too early (e.g., retrievein before retrieve)
  private String retrieveZState(String yyt)
  {
    if (yyt.startsWith(ZString.ZSTATE))
      return ZString.ZSTATE;
    else if (yyt.startsWith(ZString.ZSTINIT))
      return ZString.ZSTINIT;
    else if (yyt.startsWith(ZString.ZSTFIN))
      return ZString.ZSTFIN;
    else if (yyt.startsWith(ZString.ZASTATE))
      return ZString.ZASTATE;
    else if (yyt.startsWith(ZString.ZCSTATE))
      return ZString.ZCSTATE;
    else if (yyt.startsWith(ZString.ZAINITIN))
      return ZString.ZAINITIN;
    else if (yyt.startsWith(ZString.ZCINITIN))
      return ZString.ZCINITIN;
    else if (yyt.startsWith(ZString.ZASTINIT))
      return ZString.ZASTINIT;
    else if (yyt.startsWith(ZString.ZCSTINIT))
      return ZString.ZCSTINIT;
    else if (yyt.startsWith(ZString.ZASTFIN))
      return ZString.ZASTFIN;
    else if (yyt.startsWith(ZString.ZAFINOUT))
      return ZString.ZAFINOUT;
    else if (yyt.startsWith(ZString.ZCFINOUT))
      return ZString.ZCFINOUT;
    else if (yyt.startsWith(ZString.ZCSTFIN))
      return ZString.ZCSTFIN;
    else if (yyt.startsWith(ZString.ZRETRIEVEIN))
      return ZString.ZRETRIEVEIN;
    else if (yyt.startsWith(ZString.ZRETRIEVEOUT))
      return ZString.ZRETRIEVEOUT;
    else if (yyt.startsWith(ZString.ZRETRIEVE))
      return ZString.ZRETRIEVE;
    else
      throw new ScanException(dialect_, "Unknown Z state " + yyt +  	
	    	" for " + dialect_.toString() + " in Latex2Unicode parser.", getLocation());
  }
*/

  /**
   * A brace type enumeration.
   */
  private enum BraceType
  {
    /**
     * Subscript enclosed in braces.
     */
    SUB,

    /**
     * Superscript enclodes in braces.
     */
    SUPER,

    /**
     * Subscript enclosed in braces and space has to be added after
     * all superscripts and subscripts.
     */
    SUB_SPACE,

    /**
     * Superscript enclosed in braces and space has to be added after
     * all superscripts and subscripts.
     */
    SUPER_SPACE,

    /**
     * The brace that starts a name, for example a schema name.
     */
    NAME,

    /**
     * Like name, but for theorem environments. This is needed to possibly add an space to the token stream
     * in circumstances like \\begin{theorem}{Test} e \in X \iff true \end{theorem}. Without the space, we
     * get "Teste" as the name (!)
     */
    THM_NAME,

    

    /**
     * The brace that starts a proof name in \\begin{zproof}[NAME
     */
    PROOF_NAME,

    /**
     * The brace after \\znote{...} comment
     */
    ZNOTE,

    /**
     * The brace after \\Label{...}
     */
    ZLABEL,

    SYNDEF,
    

    /**
     * All remaining braces.
     */
    BRACE;
  }
%}




/* white spaces */
NL = "\n" | "\r" | "\r\n"
WS = [\ \t\b\012] | {NL}

/* hard spaces */
HS = "~" | "\\," | "\\:" | "\\;" | "\\ "
  | "\\t1" | "\\t2" | "\\t3" | "\\t4" | "\\t5" | "\\t6" | "\\t7"
  | "\\t8" | "\\t9" 
  
NOT_NL = !(![^] | {NL})
COMMENT = "%" ~{NL}
IGNORE = {WS} | {COMMENT}

LETTER = [a-zA-Z]
// NOT_LETTER = !(![^] | {LETTER}) // DECLARED BUT NEVER USED, COMMENTED FOR NOW. TODO: remove?

COMMAND = "\\" . | "\\" {LETTER}*
SCRIPT = "^" | "_"
FUNCTION = "*" | "+" | "|"
PUNCTATION = ";" | ","
RELATION = ":" | "<" | "=" | ">"

START_COMMENT = "%" 
CHAR_MARKUP = "%%Zchar" {NOT_NL}* {NL}?
         | "%%Zinchar" {NOT_NL}* {NL}?
         | "%%Zprechar" {NOT_NL}* {NL}?
         | "%%Zpostchar" {NOT_NL}* {NL}?
WORD_MARKUP = "%%Zword"
INWORD_MARKUP = "%%Zinword"
PREWORD_MARKUP = "%%Zpreword"
POSTWORD_MARKUP = "%%Zpostword"
FILE_MARKUP = "%%Zfile" {NOT_NL}* {NL}
LOC_MARKUP = "%%Zloc" [\ \t]* [0-9]* "#" [0-9]* "@" [0-9]* {NL}

SPECIAL = "\\" | "%"
NOT_SPECIAL = !(![^] | {SPECIAL})
TEXT = {NOT_SPECIAL}*

// NOT_LB = anything that is not "{"
NOT_LB = !(![^] | "{")
NAME =   {NOT_LB}*
       | {NOT_LB}* "{" {NOT_LB}* "}" {NOT_LB}*

//ZSTATE = "zstate" | "zstinit" | "zstfin" | "zastate" | "zcstate" | "zastinit" | "zcstinit" |
//         "zretrievein" | "zretrieveout" | "zretrieve" | "zastfin" | "zcstfin" |
//         "zainitin" | "zafinout" | "zcinitin" | "zcfinout"
//ZSTATE_INFO = "\\" {ZSTATE}


/*
A proof word is any combination of proof word parts followed by remaining bits
separated by IGNORE, which contains white space followed by possible more white
space or a comment. And similarly for proof commands (e.g., prover interaction
rather than proof script itself).

ex:
prove%blablabla
by
    %....
    reduce;

Given the lexis for proofs in LaTeX is a mix between a verbatim-like environment
(e.g., proof words parts/cmds are in plain text) and Z-LaTeX (e.g., expressions,
names, and predicates are marked-up); and the fact only CZT uses Unicode as an
intermediary, it implies a solution within the LaTeX2Uniode lexer. That is, to have
these "words" lexed specially when within a zproof environment. Note that means
the user can still have then as identifiers (e.g., they are not "keywords" as such,
but rather like implicit LaTeX markup declarations in plain text).

They get lexed as LatexSym.PROOFWORD with the given spelling. The ContextFreeScanner
then treats these as special as well, whereas the KeywordScanner is the one to identify
and finally categorise the more complicated (Mixfix with Z lexis in the middle) proof
commands (e.g., apply Z(THM)-NAME to expression Z-EXPR).

An alternative design would be to have a separate ZPROOF lexical state, but that
brings (more complicated?) problems, given the Z lexis is needed within many proof
commands to lex expr/pred/names. Instead, we use a "flag" carrying on the expected
behaviour ahead. This is not quite "context-free", but it is not unheard off either
(e.g., BraceStack state information to control braces balancing within LaTeX and
Z-LaTeX lexemes).

*A simpler alternative would be good, but this will suffice for now.*
*/

PROOFWORDHEAD    = "apply" | "back" | "cases" | "conjunctive" | "disjunctive" | "equality" |
                   "instantiate" | "invoke" | "next" | "prenex" | "prove" | "oops" | "rearrange" |
                   "reduce" | "rewrite" | "simplify" | "sorry" | "split" | "trivial" | "try" | "use" | "with"

PROOFWORDFOLLOWS = "by" | "enabled" | "disabled" | "lemma" |
                   "normalization" | "substitute"

PROOFWORDMIXED   = "expression" | "predicate" | "to"

PROOFWORDPART    = {PROOFWORDHEAD} | {PROOFWORDFOLLOWS} | {PROOFWORDMIXED}

PROOFWORD = {PROOFWORDPART} {IGNORE}* // maybe followed by ; directly as in "reduce;"

ABILITY = "enabled" | "disabled"
USAGE   = "axiom" | "frule" | "grule" | "rule"



%state ZED
%state MARKUP
%state COMMENT_STATE
%state ERROR

%state ZNOTE_STATE
%state ZLABEL_STATE


%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
  "\\begin" {IGNORE}* "{axdef}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("axdef");
          inAxiomaticBox_ = true;
          return result(ZString.AX);
        }
  "\\begin" {IGNORE}* "{gendef}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("gendef");
          inAxiomaticBox_ = true;
          return result(ZString.GENAX);
        }
  
  "\\begin" {IGNORE}* "[disabled]" "{schema}" {IGNORE}* "{" / {NAME} "}" {IGNORE}* "["
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("schema");
          inSchemaBox_ = true;
          braceStack_.push(BraceType.NAME);
          return result(ZString.SCHCHAR + ZString.GENCHAR + ZEvesString.DISABLEDDEFTAG);
        }
  
  "\\begin" {IGNORE}* "{schema}" {IGNORE}* "{" / {NAME} "}" {IGNORE}* "["
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("schema");
          inSchemaBox_ = true;
          braceStack_.push(BraceType.NAME);
          return result(ZString.SCHCHAR + ZString.GENCHAR);
        }
  
  "\\begin" {IGNORE}* "[disabled]" "{schema}" {IGNORE}* "{"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("schema");
          inSchemaBox_ = true;
          braceStack_.push(BraceType.NAME);
          return result(ZString.SCH + ZEvesString.DISABLEDDEFTAG);
        }
  
  "\\begin" {IGNORE}* "{schema}" {IGNORE}* "{"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("schema");
          inSchemaBox_ = true;
          braceStack_.push(BraceType.NAME);
          return result(ZString.SCH);
        }
  "\\begin" {IGNORE}* "{schema}" {IGNORE}*
        {
          yybegin(ERROR);
        }
  
  "\\begin" {IGNORE}* "[disabled]" "{zed}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("zed");
          return result(ZString.ZED + ZEvesString.DISABLEDDEFTAG);
        }
  
  "\\begin" {IGNORE}* "{zed}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("zed");
          return result(ZString.ZED);
        }
  "\\begin" {IGNORE}* "{zsection}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("zsection");
          return result(LatexSym.SECT, ZString.ZED);
        }
  
  "\\begin" {IGNORE}* "[disabled]" {IGNORE}* "{" / "theorem}"
        {
          yybegin(ZED);
          assert ! addSpace_;
          begin("theorem");
          braceStack_.push(BraceType.THM_NAME);
          return result(ZString.ZED + ZEvesString.DISABLEDTHMTAG);
        }
  
  "\\begin" {IGNORE}* "{" / "theorem}"
        {
          // CZT extension: pass theorem name into Unicode output.
          yybegin(ZED);
          assert ! addSpace_;
          begin("theorem");
          braceStack_.push(BraceType.THM_NAME);
          //System.out.println("BEGIN-THM = " + yytext());
          return result(ZString.ZED);
          // leave the "theorem" to be treated as a name.
        }
/*  "\\begin" {IGNORE}* "{theorem}" "{" {NAME} "}"
        {
          // The Z standard ignores the NAME part of \begin{theorem}
          yybegin(ZED);
          assert ! addSpace_;
          begin("theorem");
          return result(ZString.ZED);
        }
*/

// TODO: CircusConf: could try a more complex use of \circconf{X} for variables?

  "\\begin" {IGNORE}* "{zproofsection}" {IGNORE}* "{" / {NAME} "}" {IGNORE}* "{"
        {
          yybegin(ZED);//ZPROOF);
          assert ! addSpace_;
          begin("zproofsection");
          braceStack_.push(BraceType.NAME);
          return result(ZEvesString.ZPROOFSECTION);
        }
   // Here there is the problem that the environment is "\begin{zproof}[Name]" with "[...]"
  "\\begin" {IGNORE}* "{zproof}" {IGNORE}* "["
        {
          yybegin(ZED);//ZPROOF);
          assert ! addSpace_;
          begin("zproof");
          inProofScript_ = true;
          braceStack_.push(BraceType.PROOF_NAME);
          return result(ZEvesString.ZPROOF);
        }

  // For now, don't accept \\syndef; instead we just directly translate CZT Optemp into ZEves' XML format
  // Given \\syndef are not that common within our legacy specs, this shoulnd't be a problem. The point is
  // that we still have the ability to handle ZEves specs with op templates. The CZT Optemp language is
  // richer that ZEves', so the CZT2ZEves xml printer guarantees they match, raising an error where they can't.
  //
  //"\\syndef{" / ({NAME} "}{" {ZEVES_OP_CLS} "}{" {NAME} "}")
  //      {
  //        yybegin(ZEVES_OPT_STATE);
  //        assert ! addSpace_;
  //        braceStack_.push(BraceType.SYNDEF);
  //        synDefOpName_ = null;
  //        return result(ZString.ZED);
  //      }

  //"\\syndef{" {COMMAND} "}{infun6}{" ({LETTER} | [0-9])* "}"
  //      {
  //        System.out.println("SYNDF = " + yytext());
  //      }

  /* Nameless proofs are acceptable: they are the proof for the last declared Paragraph.
     for simplicity, rule then out here
  "\\begin" {IGNORE}* "{zproof}" {IGNORE}*
        {
          yybegin(ZED);//ZPROOF);
          assert ! addSpace_;
          begin("zproof");
          inProofScript_ = true;
          return result(ZEvesString.ZPROOF);
        }
  */
  "\\begin" {IGNORE}* "{zproof}" {IGNORE}* 
        {
          yybegin(ERROR);
        }


  ^{CHAR_MARKUP}
        {
          return result(LatexSym.CHAR_MARKUP);
        }
  ^{WORD_MARKUP}
        {
          yybegin(MARKUP);
          directive_ = true;
          return result(LatexSym.WORD_MARKUP, null);
        }
  ^{INWORD_MARKUP}
        {
          yybegin(MARKUP);
          directive_ = true;
          return result(LatexSym.INWORD_MARKUP, null);
        }
  ^{PREWORD_MARKUP}
        {
          yybegin(MARKUP);
          directive_ = true;
          return result(LatexSym.PREWORD_MARKUP, null);
        }
  ^{POSTWORD_MARKUP}
        {
          yybegin(MARKUP);
          directive_ = true;
          return result(LatexSym.POSTWORD_MARKUP, null);
        }
  ^{FILE_MARKUP}
        {
          int position = "%%Zfile".length();
          while (Character.isWhitespace(yycharat(position))) {
            position++;
          }
          filename_ = yytext().substring(position, yytext().length() - 1);
        }
  ^{LOC_MARKUP}
        {
          int start = "%%Zloc".length();
          while (Character.isWhitespace(yycharat(start))) {
            start++;
          }
          int finish = start + 1;
          while (Character.isDigit(yycharat(finish))) {
            finish++;
          }
          locLine = Integer.parseInt(yytext().substring(start, finish));
          start = finish + 1;
          finish = start + 1;
          while (Character.isDigit(yycharat(finish))) {
            finish++;
          }
          locColumn = Integer.parseInt(yytext().substring(start, finish));
          locChar = Integer.parseInt(yytext().substring(finish + 1, yytext().length() - 1));
          locyyline = yyline + 1;
          locyychar = yychar;
        }
  {START_COMMENT}
        {
          yybegin(COMMENT_STATE);
        }
  {TEXT}
        {
          return result(LatexSym.TEXT);
        }
  [^]
        {
          return result(LatexSym.TEXT);
        }
}

<ZED> {
  {NL}
        {
          if (directive_) {
            yybegin(YYINITIAL);
            directive_ = false;            
            addSpace_ = false; // TODO: what about spaces?
            return result(LatexSym.END_MARKUP);
          }
        }

  
  "\\Label" {IGNORE}* "{" / ({ABILITY}? {USAGE}? {NAME} "}")
        {
          addSpace_ = true;
          String label = yytext();
          //System.out.println("SEEN LABEL = " + label);
          if (!inAxiomaticBox_)
          {
            reportError(ErrorType.ERROR, ZEvesParseMessage.MSG_NOT_WITHIN_AXDEF, label, label);
          }
          yybegin(ZLABEL_STATE);
          braceStack_.push(BraceType.ZLABEL);
          return result(ZEvesString.LLABEL + ZString.SPACE);
        }
  "\\znote" {IGNORE}* "{"
        {
           //System.out.println("SEEN ZNOTE");
           yybegin(ZNOTE_STATE);
           braceStack_.push(BraceType.ZNOTE);
           return result(ZEvesString.LZNOTE);
        }
  
  {IGNORE}
        {
          /* ignore whitespace (except NL) and comments */
        }
  {HS}
        {
          String result = addSpace();
          result += ZString.SPACE;
          return result(result);
        }
  "\\\\" | "\\also" | "\\znewpage" 
        {
          String result = addSpace();
          return result(result + ZString.NLCHAR);
        }        

  "\\end" {IGNORE}* "{theorem}"
        {
          end("theorem");

             yybegin(YYINITIAL);

          catchingThmName_ = false;
          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }
  "\\end" {IGNORE}* "{axdef}"
        {
          end("axdef");

             yybegin(YYINITIAL);

          String result = addSpace() + ZString.END;
          inAxiomaticBox_ = false;
          return result(LatexSym.END, result);
        }
  "\\end" {IGNORE}* "{gendef}"
        {
          end("gendef");

             yybegin(YYINITIAL);

          String result = addSpace() + ZString.END;
          inAxiomaticBox_ = false;
          return result(LatexSym.END, result);
        }
  "\\end" {IGNORE}* "{schema}"
        {
          end("schema");
          inSchemaBox_ = false;

             yybegin(YYINITIAL);

          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }
  "\\end" {IGNORE}* "{zed}"
        {
          end("zed");

             yybegin(YYINITIAL);

          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }
  "\\end" {IGNORE}* "{zsection}"
        {
          end("zsection");

             yybegin(YYINITIAL);

          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }



    "\\end" {IGNORE}* "{zproofsection}"
        {
          end("zproofsection");
          yybegin(YYINITIAL);
          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }
    "\\end" {IGNORE}* "{zproof}"
        {
          end("zproof");
          inProofScript_ = false;
          yybegin(YYINITIAL);
          String result = addSpace() + ZString.END;
          return result(LatexSym.END, result);
        }


/*
  {ZSTATE_INFO} 
        {
          String zst = yytext();
          if (!inSchemaBox_)
          {
            reportError(ErrorType.ERROR, ZParseMessage.MSG_NOT_WITHIN_SCHBOX, zst, zst);
          }
          zst = retrieveZState(zst.substring(1, zst.length())); // remove the "\\"
          return result(zst);
        }
  "\\zfsrefines" {WS} / {NAME}
        {
          if (!inSchemaBox_)
          {
            final String zst = yytext();
            reportError(ErrorType.ERROR, ZParseMessage.MSG_NOT_WITHIN_SCHBOX, zst, zst);
          }
          assert ! addSpace_;
          return result(ZString.ZFSREFINES + ZString.SPACE);
        }

  "\\zbsrefines" {WS} / {NAME}
        {
          if (!inSchemaBox_)
          {
            final String zst = yytext();
            reportError(ErrorType.ERROR, ZParseMessage.MSG_NOT_WITHIN_SCHBOX, zst, zst);
          }
          assert ! addSpace_;
          return result(ZString.ZBSREFINES + ZString.SPACE);
        }
*/

  "\\where"
        {
          String result = addSpace();
          return result(result + ZString.SPACE + ZString.VL + ZString.SPACE);
        }
  "@"
        {
          String result = addSpace();
          return result(result + ZString.SPACE + ZString.SPOT + ZString.SPACE);
        }
  "'"
        {
          String result = addSpace();
          return result(result + ZString.PRIME);
        }
  "-"
        {
          String result = addSpace();
          result += result + ZString.SPACE + ZString.MINUS;
          if (braceStack_.empty()) {
            result += ZString.SPACE;
          }
          return result(result);
        }
  {SCRIPT} {IGNORE}* ({RELATION}|{PUNCTATION}|{FUNCTION}|{LETTER}|[0-9])
        {
          String script = yytext().substring(0, 1);
          return result(beginScript(script)
                        + yytext().substring(yytext().length() - 1)
                        + endScript(script));
        }
  {SCRIPT} {IGNORE}* {COMMAND}
        {
          String result = "";
          String script = yytext().substring(0, 1);
          String command = yytext().substring(yytext().indexOf("\\"));
          String zstring = toUnicode(command, false);
          result += beginScript(script);
          if (zstring != null) {
            result += zstring;
          } else {
            reportUnknownLatexCommand(command);
            result += command.substring(1);
          }
          result += endScript(script);
          return result(result);
        }
  {SCRIPT} {IGNORE}* "{"
        {
          String script = yytext().substring(0, 1);
          if ("^".equals(script)) {
            if (addSpace_) {
              braceStack_.push(BraceType.SUPER_SPACE);
            } else {
              braceStack_.push(BraceType.SUPER);
            }
          } else if ("_".equals(script)) {
            if (addSpace_) {
              braceStack_.push(BraceType.SUB_SPACE);
            } else {
              braceStack_.push(BraceType.SUB);
            }
          }
          addSpace_ = false;
          return result(beginScript(script));
        }
  {SCRIPT} {IGNORE}* .
        {
          String message = "Unexpected subscript or superscript " + yytext() + 
          	" for " + dialect_.toString() + " in Latex2Unicode parser.";
          throw new ScanException(dialect_, message, getLocation());
        }
  "\\_" | "\\{" | "\\}"
        {
          String result = addSpace();
          return result(result + yytext().substring(1));
        }
  "\\SECTION"
        {
          String result = addSpace() + "section";
          addSpace_ = true;
          return result(LatexSym.SECTION, result);
        }
  "\\parents"
        {
          String result = addSpace() + ZString.SPACE + "parents";
          addSpace_ = true;
          return result(LatexSym.PARENTS, result);
        }
  {COMMAND}
        {
          String result = addSpace();
          boolean spaces = braceStack_.empty();
          String zstring = toUnicode(yytext(), spaces);
          if (zstring != null) {
            result += zstring;
          }
          else {
            reportUnknownLatexCommand(yytext());
            if (spaces) result += ZString.SPACE;
            result += yytext().substring(1);
            if (spaces) result += ZString.SPACE;
          }
          return result(result);
        }
  "{"
        {
          String result = addSpace();
          braceStack_.push(BraceType.BRACE);
          return result(result);
        }
  "}"
        {
          String result = "";
          if (braceStack_.empty()) {
            final ErrorType errorType = ErrorType.ERROR;
            reportError(errorType, ZParseMessage.MSG_UNMATCHED_BRACES, "}");
            return result(result);
          }
          BraceType brace = (BraceType) braceStack_.pop();
          assert ! addSpace_;
          if (brace.equals(BraceType.SUPER)) {
            result += ZString.SW;
          } else if (brace.equals(BraceType.SUPER_SPACE)) {
            result += ZString.SW;
            addSpace_ = true;
          } else if (brace.equals(BraceType.SUB)) {
            result += ZString.NW;
          } else if (brace.equals(BraceType.SUB_SPACE)) {
            result += ZString.NW;
            addSpace_ = true;
          } else if (brace.equals(BraceType.NAME)) {
            result += ZString.SPACE;
          }
          // Leo: I don't like this assymetry. ThmName should be just like any other name!
          //      \vdash? theorems with just a Name are not a good idea (!)
          else if (brace.equals(BraceType.THM_NAME))
          {
            //seenThm_ = false;
            // up to here, the Theorem NAME must have been seen... !! NO. This is the \begin{theorem '}'--- brace !!! useless...
            catchingThmName_ = true;
            result += ZString.SPACE;
          }
          // this is to flag that "thmName" catching has finished.
          else if (brace.equals(BraceType.BRACE) && catchingThmName_)
          {
            catchingThmName_ = false;
            result += ZString.SPACE;
          }
          //System.out.println("CLOSING-BRACE " + brace.toString() + " catchingThmName_ = " + catchingThmName_ + " = '" + result + "'");
          return result(result);
        }
  
  "applies\\$to"
        {
          String result = yytext();
          addSpace_ = true;
          result = result.replaceAll("\\\\\\$","\\$");
          return result(result);
        }

  {PROOFWORD}
        {
          String proofWord = yytext();
          // some proof words *must* be separated
          if (proofWord.equals("to") || proofWord.equals("expression") || proofWord.equals("predicate")) addSpace_ = true;

          // if caught by mistake outside a z proof environment (e.g., "by" or "prove" as var name?)
          // report the error and carry on to allow for better recovery later on, rather than hard crash
          // with throw new ScanException. TODO: which is better?

          // added a comparison for "to" for the *very* specific "applies$to" operator :-(
          if (!inProofScript_ && proofWord.compareToIgnoreCase("to") != 0)
          {
            //reportError(ErrorType.ERROR, ZEvesParseMessage.MSG_NOT_WITHIN_PROOFSCRIPT, proofWord, proofWord);//no extra information
            reportError(ErrorType.WARNING, ZEvesParseMessage.MSG_NOT_WITHIN_PROOFSCRIPT, proofWord, proofWord);//no extra information
            return result(proofWord); // return it as just unicode
          }
          //System.out.println("SEE PROOF-WORD-IN-ZED-STATE = '" + proofWord + "'; inProofScript? = " + inProofScript_);
          return result(LatexSym.PROOFWORD, proofWord);
        }
  {USAGE}
        {
          String usage = yytext();
          //System.out.println("SEEN USAGE-IN-ZED-STATE = " + usage);
          return result(LatexSym.PROOFWORD, usage + ZString.SPACE);
        }
        // either a proof word after white-space, or an empty proof environment
   "]" / ({IGNORE}* ({PROOFWORD} | "\\end{zproof}"))
        {
          String result = "";
          assert inProofScript_;
          assert ! addSpace_;
          if (braceStack_.empty()) {
            final ErrorType errorType = ErrorType.ERROR;
            reportError(errorType, ZParseMessage.MSG_UNMATCHED_BRACES, "]");
            return result(result);
          }
          BraceType brace = (BraceType) braceStack_.pop();
          assert ! addSpace_;
          if (brace.equals(BraceType.PROOF_NAME))
          {
            result += ZString.SPACE;
          }
          else
          {
            final ErrorType errorType = ErrorType.ERROR;
            reportError(errorType, ZParseMessage.MSG_UNMATCHED_BRACES, "]");
          }
          return result(result);
        }
  
  {FUNCTION} | {RELATION}({RELATION}|{WS})*
        {
          // To aid low-level debugging: {FUNCTION} | {RELATION}({RELATION}|{WS})*
          String result = addSpace();
          if (braceStack_.empty()) {
            result += ZString.SPACE;
          }
          result += yytext().replaceAll("[ ]", "");
          if (braceStack_.empty()) {
            addSpace_ = true;
          }
          return result(result);
        }
  {PUNCTATION}
        {
          // To aid low-level debugging: {PUNCTATION}
          String result = addSpace();
          result += yytext();
          if (braceStack_.empty()) addSpace_ = true;
          return result(result);
        }
  ({LETTER} | [0-9]  | "\\$" )*
        {
          // To aid low-level debugging: ({LETTER} | [0-9])*
          String result = addSpace();
          result += yytext();
          
          // replaces \$ for $
          result = result.replaceAll("\\\\\\$","\\$");
          
          //System.out.println("LEXING-DECORWORD AFTER FLAG (" + catchingThmName_ + ") = '" + result + "'");
          return result(result);
        }
  .
        {
          // To aid low-level debugging: .
          String result = addSpace();
          result += yytext();
          //System.out.println("CATCH-ALL = '" + result + "'");
          return result(result);
        }
}

<MARKUP> {
  {WS}
        {
          /* ignore whitespaces */
        }
  {COMMAND}
        {
          yybegin(ZED);
          return result(LatexSym.NAME);
        }
  .
        {
          yybegin(YYINITIAL);
          reportError(ErrorType.ERROR,
                      ZParseMessage.MSG_UNEXPECTED_TOKEN,
                      yytext());
        }
}

<COMMENT_STATE> {
  {NL}
       {
         yybegin(YYINITIAL);
       }
  .
       {
         // do nothing
       }
}


<ZNOTE_STATE> {
  "}"
      {
        yybegin(ZED);

        // check this is a matching "}" for "\\znote{"
        boolean okay = !braceStack_.empty();
        BraceType brace = null;
        if (okay)
        {
          brace = (BraceType) braceStack_.pop();
          okay = brace.equals(BraceType.ZNOTE);
        }
        if (!okay) {
          final ErrorType errorType = ErrorType.ERROR;
          reportError(errorType, ZParseMessage.MSG_UNMATCHED_BRACES, "}");
        }
        return result(ZEvesString.RZNOTE);
      }
  .
      {
      // just return  the text
        return result(yytext());
      }
}

<ZLABEL_STATE> {
  {ABILITY}
      {
        String result = yytext();
        return result(LatexSym.PROOFWORD, result);
      }
  {USAGE}
      {
        String result = yytext();
        return result(LatexSym.PROOFWORD, result);
      }

  "}"
      {
        // check this is a matching "}" for "\\Label{"
        boolean okay = !braceStack_.empty();
        BraceType brace = null;
        if (okay)
        {
          brace = (BraceType) braceStack_.pop();
          okay = brace.equals(BraceType.ZLABEL);
        }
        if (!okay) {
          final ErrorType errorType = ErrorType.ERROR;
          reportError(errorType, ZParseMessage.MSG_UNMATCHED_BRACES, "}");
        }
        yybegin(ZED);
        return result(ZEvesString.RLABEL);
      }

  ({LETTER} | [0-9])*
        {
          // To aid low-level debugging: ({LETTER} | [0-9])*
          String result = addSpace();
          result += yytext();
          return result(result);
        }
  .
        {
          // To aid low-level debugging: .  // OR SHOULD THIS BE A CATCH ALL ERROR? No good to pass to the parser as DECORWORD
          String result = addSpace();
          result += yytext();
          return result(result);
        }
}


<ERROR> {
  .
        {
          yybegin(YYINITIAL);
          reportError(ErrorType.ERROR,
                      ZParseMessage.MSG_UNEXPECTED_TOKEN_NAME_EXPECTED,
                      yytext());
        }
}
