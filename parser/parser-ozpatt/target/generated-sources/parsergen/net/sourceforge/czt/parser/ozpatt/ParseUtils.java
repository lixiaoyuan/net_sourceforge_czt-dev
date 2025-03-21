
package net.sourceforge.czt.parser.ozpatt;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import org.xml.sax.InputSource;

import net.sourceforge.czt.base.ast.Term;
import net.sourceforge.czt.base.util.UnmarshalException;
import net.sourceforge.czt.parser.util.ParseException;
import net.sourceforge.czt.z.util.Version;
import net.sourceforge.czt.z.util.ZString;
import net.sourceforge.czt.z.util.ZUtils;

import net.sourceforge.czt.session.AbstractCommand;
import net.sourceforge.czt.session.Command;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.Markup;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.SectionManager;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.session.StringSource;
import net.sourceforge.czt.session.DefaultSectionParents;
import net.sourceforge.czt.util.Section;
import net.sourceforge.czt.z.ast.AxPara;
import net.sourceforge.czt.z.ast.ConstDecl;
import net.sourceforge.czt.z.ast.Expr;
import net.sourceforge.czt.z.ast.Para;
import net.sourceforge.czt.z.ast.Pred;
import net.sourceforge.czt.z.ast.Sect;
import net.sourceforge.czt.z.ast.Spec;
import net.sourceforge.czt.z.ast.ZSect;

import net.sourceforge.czt.session.Dialect;


import net.sourceforge.czt.oz.jaxb.JaxbXmlReader;


/**
 * Utilities for parsing specifications.
 *
 * @author Petra Malik, Tim Miller, Leo Freitas, Andrius Velykis
 */
public class ParseUtils extends AbstractCommand
{

  // dialect is a final attribute determined at construction time
  // depending on the extension being used (i.e. decided at XML transformation time).
  // this is useful for fine-tuned error information. If no extension is chosen we 
  // get a compilation error (i.e. final field not assigned).
  static final Dialect dialect_ = 
  						Dialect.OZPATT
    				  // otherwise it will be unassigned, hence a compilation error
    				  ;
    				  
  public static Term parse(Source source, SectionInfo sectInfo)
    throws ParseException, IOException, UnmarshalException
  {
    return parse(source, sectInfo, new Properties());
  }

  public static Term parse(Source source,
                           SectionInfo sectInfo,
                           Properties properties)
    throws ParseException, IOException, UnmarshalException
  {
    return parse0(source, sectInfo, properties, Collections.<Key<?>>emptySet());
  }

  private static Term parse0(Source source,
                           SectionInfo sectInfo,
                           Properties properties,
                           Collection<? extends Key<?>> explicitDeps)
    throws ParseException, IOException, UnmarshalException
  {
    traceLog("PU-parse-source = " + source);
    Term result = null;
    if (source.getMarkup() == Markup.LATEX) {
      LatexParser parser = new LatexParser(source, sectInfo, properties, explicitDeps);
      result = parser.parse();
      parser = null;
    }
    else if (source.getMarkup() == Markup.UNICODE) {
      UnicodeParser parser = new UnicodeParser(source, sectInfo, properties, explicitDeps);
      result = parser.parse();
      parser = null;
    }
    else if (source.getMarkup() == Markup.ZML)
    {
      JaxbXmlReader reader = new JaxbXmlReader();
      Reader in = source.getReader();
      try {
        result = reader.read(new InputSource(in));
      } finally {
        in.close();
      }
      if (result instanceof Spec) {
        Spec spec = (Spec) result;
        for (Sect sect : spec.getSect()) {
          if (sect instanceof ZSect)
          {
            ZSect zSect = (ZSect) sect;

            // Latex and unicode parsers put ZSect into SectMan themselves. Here we put the
            // parsed ZSect as well for a uniform solution.
            //
            // No need for section manager transaction since section management is not used here.
            // However, we need to add explicit dependencies such as source and spec.
            //
            // The InfoTables or LMF won't be needed: if they are requested, their respective commands
            // will calculate them for the user.
            sectInfo.put(new Key<ZSect>(zSect.getName(), ZSect.class), zSect, explicitDeps);
          }
        }
      }
      else if (result instanceof ZSect) {
        ZSect zSect =  (ZSect) result;
        // add the section directly to the manager --- same as above
        sectInfo.put(new Key<ZSect>(zSect.getName(), ZSect.class), zSect, explicitDeps);
      }
      reader = null;
    }
    else {
      throw new UnsupportedOperationException("Invalid source markup " + source.getMarkup() + " for dialect " + sectInfo.getDialect() + " in ParseUtils.");
    }
    assert result != null;
    traceLog("PU-parse-result = " + result.getClass().getSimpleName());
    return result;
  }
  
  private static final Key<DefaultSectionParents> defaultSPKey_ = 
  		new Key<DefaultSectionParents>(Section.ANONYMOUS.getName(),
        		DefaultSectionParents.class);
  
  public static String getDefaultAnonymousParentsAsString(SectionManager sectman)
  	throws CommandException
  {
  	 DefaultSectionParents dsp = sectman.get(defaultSPKey_);
     Set<String> parentSet = dsp.defaultParents(defaultSPKey_.getName());
     return ZUtils.parentsAsString(parentSet);
  }

  /** Parse a Source string/file as a Z Predicate.
   *  @param src The String or File etc. to be parsed.
   *  @param section The Z section name (in Unicode)
   *                 to parse within (null=standard_toolkit).
   *  @param sectman A section manager to use.  Must be non-null.
   *
   * @return 
   * @throws IOException if there are errors reading file/url inputs
   * @throws CommandException if the predicate cannot be parsed.
   */
  public static Pred parsePred(Source src, String section,
			       /*@non_null@*/SectionManager sectman) 
    throws IOException, CommandException
  {
    // TODO: Add backslash in front of underscores
    String parent = section;
    String name = "CZT_parsePred_tmp1433589372849";
    StringBuilder buf = new StringBuilder();
    if (src.getMarkup() == Markup.LATEX) {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append("\\begin{zsection}\\SECTION ");
      buf.append(name);
      buf.append(" \\parents ");
      buf.append(parent.replaceAll("_", "\\\\_"));
      buf.append("\\end{zsection} ");
      buf.append("\\begin{axdef} \\where\n");
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
      buf.append("\n\\end{axdef}\n");
    }
    else {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append(ZString.ZED);
      buf.append(" section ");
      buf.append(name);
      buf.append(" parents ");
      buf.append(parent);
      buf.append(ZString.END);
      buf.append(ZString.AX);
      buf.append(ZString.BAR);
      buf.append(ZString.NL);
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
      buf.append(ZString.NL);
      buf.append(ZString.END);
    }
    // we create a temporary sectman so that this temp section does not
    //  clutter up the original section manager.
    SectionManager tmpsectman = sectman.clone();
    Source input = new StringSource(buf.toString(), name);
    input.setMarkup(src.getMarkup());
    input.setEncoding(src.getEncoding());
    tmpsectman.put(new Key<Source>(name, Source.class), input);
    // use the parser itself to create objects - makes easier section management.
    Spec spec = tmpsectman.get(new Key<Spec>(name, Spec.class));
    AxPara axPara = firstAxPara(spec);
    assert axPara != null;
    tmpsectman = null;
    return axPara.getZSchText().getPred();
  }

  private static AxPara firstAxPara(Spec spec)
  {
    ZSect sect = (ZSect) spec.getSect().get(0);
    for (Object p : ZUtils.assertZParaList(sect.getParaList())) {
      if (p instanceof AxPara) {
        return (AxPara) p;
      }
    }
    return null;
  }

  /** Parse a Source string/file as a Z Expression.
   *  @param src The String or File etc. to be parsed.
   *  @param section The Z section (in Unicode)
   *                 to parse within (null=standard_toolkit).
   *  @param sectman A section manager to use.  Must be non-null.
   *
   * @return
   * @throws IOException if there are errors reading file/url inputs
   * @throws CommandException if the expression cannot be parsed.
   */
  public static Expr parseExpr(Source src, String section,
			       /*@non_null@*/SectionManager sectman) 
    throws IOException, CommandException
  {
    String parent = section;
    String name = "CZT_parseExpr_tmp1433589372849";
    StringBuilder buf = new StringBuilder();
    if (src.getMarkup() == Markup.LATEX) {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append("\\begin{zsection}\\SECTION ");
      buf.append(name);
      buf.append(" \\parents ");
      buf.append(parent.replaceAll("_", "\\\\_"));
      buf.append("\\end{zsection} ");
      buf.append("\\begin{axdef} result == \n");
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
      buf.append("\n\\end{axdef}\n");
    }
    else {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append(ZString.ZED);
      buf.append(" section ");
      buf.append(name);
      buf.append(" parents ");
      buf.append(parent);
      buf.append(ZString.END);
      buf.append(ZString.AX);
      buf.append("result");
      buf.append(" ");
      buf.append(ZString.DEFEQUAL);
      buf.append(" ");
      buf.append(ZString.NL);
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
      buf.append(ZString.NL);
      buf.append(ZString.END);
    }
    // we create a temporary sectman so that this temp section does not
    //  clutter up the original section manager.
    SectionManager tmpsectman = sectman.clone();
    Source input = new StringSource(buf.toString(), name);
    input.setMarkup(src.getMarkup());
    input.setEncoding(src.getEncoding());
    tmpsectman.put(new Key<Source>(name, Source.class), input);
    // Use the parser itself to create the objects - this makes section management easier
    Spec spec = tmpsectman.get(new Key<Spec>(name, Spec.class));
    AxPara axPara = firstAxPara(spec);
    assert axPara != null;
    ConstDecl cdecl = (ConstDecl) axPara.getZSchText().getZDeclList().get(0);
    tmpsectman = null;
    return cdecl.getExpr();
  }
  
  /** Parse a Source string/file as Z Paragraphs.
   *  @param src The String or File etc. to be parsed.
   *  @param section The Z section (in Unicode)
   *                 to parse within (null=standard_toolkit).
   *  @param sectman A section manager to use.  Must be non-null.
   *
   * @return
   * @throws IOException if there are errors reading file/url inputs
   * @throws CommandException if the content cannot be parsed.
   */
  public static List<Para> parseParas(Source src, String section,
                               /*@non_null@*/SectionManager sectman) 
    throws IOException, CommandException
  {
    String parent = section;
    String name = "CZT_parseParas_tmp1433589372849";
    StringBuilder buf = new StringBuilder();
    if (src.getMarkup() == Markup.LATEX) {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append("\\begin{zsection}\\SECTION ");
      buf.append(name);
      buf.append(" \\parents ");
      buf.append(parent.replaceAll("_", "\\\\_"));
      buf.append("\\end{zsection} \n");
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
    }
    else {
      if (parent == null || parent.isEmpty())
      {
        parent = getDefaultAnonymousParentsAsString(sectman);
      }
      buf.append(ZString.ZED);
      buf.append(" section ");
      buf.append(name);
      buf.append(" parents ");
      buf.append(parent);
      buf.append(ZString.END);
      Reader reader = src.getReader();
      try {
        int ch = reader.read();
        while (ch != -1) {
          buf.appendCodePoint(ch);
          ch = reader.read();
        }
      } finally {
        reader.close();
      }
    }
    // we create a temporary sectman so that this temp section does not
    //  clutter up the original section manager.
    SectionManager tmpsectman = sectman.clone();
    Source input = new StringSource(buf.toString(), name);
    input.setMarkup(src.getMarkup());
    input.setEncoding(src.getEncoding());
    tmpsectman.put(new Key<Source>(name, Source.class), input);
    // Use the parser itself to create the objects - this makes section management easier
    Spec spec = tmpsectman.get(new Key<Spec>(name, Spec.class));
    
    ZSect sect = (ZSect) spec.getSect().get(0);
    tmpsectman = null;
    return ZUtils.assertZParaList(sect.getParaList());
  }

  /**
   * Determine the markup of a file.
   * @return
   * @param filename
   */
  public static Markup getMarkup(String filename)
  {
    Markup result = null;
    if (filename.endsWith(".tex") || filename.endsWith(".TEX") ||
        filename.endsWith(".zed") || filename.endsWith(".ZED")) {
      result = Markup.LATEX;
    }
    else if (filename.endsWith("8") || filename.endsWith("16")) {
      result = Markup.UNICODE;
    }
    else if (filename.endsWith("ml")) {
      result = Markup.ZML;
    }
    return result;
  }

  /**
   * Get a <code>Command</code> object for use 
   * in <code>SectionManager</code>
   *
   * @return A command for parsing specifications.
   */
  public static Command getCommand()
  {
    return new ParseUtils();
  }

  // Command METHODS

  /**
   * <p>
   * This command parses the given ZSection or Specification with associated named source.
   * There are two cases: manager update of Z sections of cached Spec added on the fly, and
   * parsing of a named Source, most commonly FileSources.
   * </p>
   * <p>
   * For the latter, the parser updates manager with ZSect and other resources, whereas
   * the command updates the manager in case the resource is a Spec (e.g., it could be
   * just a ZSect or even Term like Expr or Pred). If the Source resource cannot be found,
   * or if a parsing error occurs, a CommandException is raised with appropriate underlying cause.
   * </p>
   *
   * -pre name.equals(ZSect.name) or name.equals(Spec.filename) or manager.isCached(Key(name,Spec))
   * -pre !manager.isCached(Key(name, Spec))    implies Okay(manager.get(Key(name, Source))
   * -post  manager.isCached(Key(name, Spec))   implies forall z: ZSect in Spec : manager.put(Key(z.name, ZSect))
   * -post !manager.isCached(Key(name, Spec))
   *       Okay(manager.get(Key(name, Source))) implies Parse(Key(name,Source)) and manager.put(Key(name, Spec))
   * @param name ZSect or Spec resource Source name
   * @param manager section manager database
   * @return irrelevant (!)
   * @throws CommandException if named Source resource cannot be found (SourceLocatorException) or
   *  if a parsing exception occurs, which can be for various reasons (ParseException, IOException, etc).
   */
  @Override
  protected boolean doCompute(String name, SectionManager manager)
    throws CommandException
  {
    traceLog("PU-compute-name = " + name);
    
    if (!dialect_.equals(manager.getDialect()))
    	throw new IllegalArgumentException("Incompatible dialects in ParseUtils. " + dialect_.toString() +
    		" expected, but section manager dialect " + manager.getDialect() + " found.");
    		    
    // First try a special case - if there is a Spec with the same name (e.g. added on the fly).
    // In that case, resolve the sections from the Spec. Otherwise, continue with full parse.
    boolean computedFromSect = computeSpecSections(name, manager);
    if (computedFromSect) {
      return true;
    }
    
    /*
     * The ParseUtils command is usually used to resolve both ZSect and Spec keys. However, even
     * during resolve of ZSect, the whole Spec is parsed - so other ZSects from the same Spec are
     * also added, and we need to track them in dependencies.
     * 
     * To achieve a uniform solution, we reorder the transactions so that the outer one is always
     * the Spec, since ZSect transactions are handled in the Parser. This is done by postponing
     * the ZSect transaction, if one exists and ensuring that a Spec transaction has started.
     */
    Key<Spec> specKey = new Key<Spec>(name, Spec.class);
    
    // Postpone the ZSect transaction started by the SectionInfo.get(ZSect).
    // Note that it may not be started, if ParseUtils is called for SectionInfo.get(Spec).
    Key<?> currentTrans = manager.getCurrentTransaction();
    if (currentTrans.getType() == ZSect.class)
    {
      manager.postponeTransaction(currentTrans, specKey);
    }

    // TODO now that we have the current transaction - can we do something about ZML Terms?
    // E.g. check if a non-Spec is requested, and do a special case for ZML terms?

    // Unless a SectionInfo.get(Spec) was performed, we need to have its transaction explicitly
    // started here.
    manager.ensureTransaction(specKey);
    
    
    /*
     * The usual parse case is when a Spec comes from a named Source, and is parsed from the file.
     * The Latex and Unicode parsers would produce a Spec, however ZML sources may give any term. In
     * these cases, Spec would be obtained - however at the moment we do not support these cases via
     * the command approach. Only the case of a singleton ZSect is supported by wrapping it into a
     * ZSect.
     */
    
    Term term;
    try {
    
      // Retrieve the Spec source
      Key<Source> sourceKey = new Key<Source>(name, Source.class);
      Source source = manager.get(sourceKey);
      
      // After we have the source, parse it and add the result Spec to the manager. The individual 
      // ZSects will be added to the section manager during the parsing (see Parser#updateZSect).
      // Indicate the explicit dependencies on the Source and the current Spec for the
      // ZSects created during parsing.
    
      term = ParseUtils.parse0(source, manager, manager.getProperties(),
        Arrays.asList(new Key<?>[] { sourceKey, specKey }));
            
    }
    catch (CommandException exception) {
      // in case of exceptions during parsing (or source resolution), 
      // cancel the manually started Spec transaction
      // (same for other exceptions)
      manager.cancelTransaction(specKey);
      throw exception;
    }
    catch (IOException exception) {
      manager.cancelTransaction(specKey);
      throw new CommandException(manager.getDialect(), exception);
    }
    catch (UnmarshalException exception) {
      manager.cancelTransaction(specKey);
      throw new CommandException(manager.getDialect(), exception);
    }
    catch (net.sourceforge.czt.util.CztException exception) {
      manager.cancelTransaction(specKey);
      throw new CommandException(manager.getDialect(), exception);
    }
    
    if (term instanceof ZSect) {
      // only parsed the ZSect, so wrap it into a Spec to be consistent
      // the ZSect should have been added to the section manager during the parsing
      term = ZUtils.FACTORY.createSpec(
          Collections.singletonList((ZSect) term), Version.ZML_VERSION);
    }
            
    if (term instanceof Spec) {
      // add explicit dependencies on its sections (which may have not been caught via 
      // SectionInfo.get()). The source is caught by the get() above.
      manager.endTransaction(specKey, (Spec) term, getSectionKeys((Spec) term));
    }
    else {
      // If we are parsing ZML files, we can get any term via parsing. However, we do not add it
      // to the section manager at the moment - this is likely to be caught by the command
      // checks later.
      // Cancel the Spec transaction, since we do not know how the term relates to the Spec
      manager.cancelTransaction(specKey);
    }


	boolean result = term != null;
    final String msg = "PU-PARSED = parse " + 
          (result ? "okay. Put " + term.getClass().getSimpleName() + " to SM." : "failed!");
    traceLog(msg);
    
    // What if result is false? The SM protocol does not use this flag! Why keep it? [Leo]
    return result;
  }
  
  /**
   * Checks whether there is a Spec available in the section manager under the given name. This is a
   * special case, e.g. when the Spec is added directly to the manager, instead of via the parsing.
   * This method adds the ZSects in the found spec, if available, to the section manager. Then they
   * can be resolved by section manager methods.
   * 
   * @param name
   *          name of the requested ZSect
   * @param manager
   *          section manager database
   * @return if the Sect was available and its sections resolved, false otherwise - meaning a full
   *         parse is needed
   * @throws CommandException
   *           if retrieving the Spec fails, though this should never happen
   */
  private static boolean computeSpecSections(String name, SectionInfo manager)
    throws CommandException
  {
    // check whether a Spec under the requested name is cached, then add its ZSects
    // to the section manager
    Key<Spec> specKey = new Key<Spec>(name, Spec.class);
    if (manager.isCached(specKey)) {
      Spec spec = manager.get(specKey);
      
      /*
       * If the Spec is cached, that means the incoming transaction is that of a ZSect, started via
       * SectionInfo.get(ZSect). We will add the sections of the given Spec to the section manager,
       * first - the requested one (ending the transaction), then the rest, each with its own 
       * immediate transaction (via SectionInfo.put()).
       */
      Key<ZSect> zsKey = new Key<ZSect>(name, ZSect.class);
      
      /*
       * Add all ZSects of the Spec to the section manager.
       * 
       * First of all, collect all ZSects in a map, and put them into the section manager afterwards.
       * This is needed for several reasons. First, we may have sections with duplicate names,
       * and putting such sections without removing the first would cause transaction problems. The
       * map will allow us to put the last section for each name only.
       * 
       * Second, by querying the map, we can control the transactions appropriately. For example, the
       * Spec may not contain the ZSect with a requested name - in that case, we need to cancel the 
       * transaction.
       */
      Map<Key<ZSect>, ZSect> specSections = new LinkedHashMap<Key<ZSect>, ZSect>();
      for (Sect sect : spec.getSect()) {
        if (sect instanceof ZSect) {
          ZSect zsect = (ZSect) sect;
          
          String sectName = zsect.getName();
          if (Section.ANONYMOUS.getName().equals(sectName)) {
            /*
             * If the Spec contains only an anonymous ZSect, by default it will be added under the
             * anonymous name to the section manager. Therefore, the section manager will not be
             * able to locate it. In addition to the default name, we add the anonymous section
             * under the requested key name as well, to complete successful command resolution.
             */
            specSections.put(zsKey, zsect);
          }
          
          // put it under the original ZSect name into the section manager
          specSections.put(new Key<ZSect>(sectName, ZSect.class), zsect);
          
          /*
           * Note that we would have a problem if the Spec would not have any sections at all, or no
           * section would be of the required name. After the command is executed, the section
           * manager would not be able to find a correct result for the requested ZSect key. But
           * this would be handled there, and would allow the user to correct the specifications.
           */
        }
      }
      
      // for all added sections, indicate an explicit dependency on the Spec they originated
      Set<Key<Spec>> specDep = Collections.singleton(specKey);
      
      // check whether the requested ZSect was located
      ZSect requestedSect = specSections.remove(zsKey);
      if (requestedSect == null) {
        // cannot locate the requested section - it may be the case that the Spec was empty, 
        // or had no ZSects with the correct name. In this case - cancel the transaction.
        manager.cancelTransaction(zsKey);
      } else {
        manager.endTransaction(zsKey, requestedSect, specDep);
      }
      
      // add the rest of the sections in individual transactions, with dependency on Spec
      for (Map.Entry<Key<ZSect>, ZSect> entry : specSections.entrySet()) {
      
        // Entries with "other" section keys could already be in the section manager.
        // Since they are added on top of the requested ZSect, only add if they are not
        // in the section manager.
        if (!manager.isCached(entry.getKey())) {
          manager.put(entry.getKey(), entry.getValue(), specDep);
        }
      }
      
      final String msg = "PU-SPEC-ONFLY-ZSECT = put ZSect for on-the-fly Spec " + name;
      traceLog(msg);
      // return true for resolving the cached Spec sections, 
      // even though they do not match the requested
      return true;
    }
    else {
      return false;
    }
  }
  
  private static Set<Key<ZSect>> getSectionKeys(Spec spec) {
    
    Set<Key<ZSect>> sectKeys = new HashSet<Key<ZSect>>();
    
    for (Sect sect : spec.getSect()) {
      if (sect instanceof ZSect) {
        sectKeys.add(new Key<ZSect>(((ZSect) sect).getName(), ZSect.class));
      }
    }
    
    return sectKeys;
  }
  
}
