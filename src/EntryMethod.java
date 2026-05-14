import Analysis.CallGraphAnalysis;
import Analysis.ImpactAnalysis;
import Analysis.TaintAnalysis;
import soot.SootMethod;

public class EntryMethod {

	protected SootMethod mIntentDump = null;

	public static void main(String[] args) {

		try 
		{
			//Perform TA for first application.
			var ta = new TaintAnalysis(".\\Android Platform\\android.jar", ".\\Definitions\\AndroidCallbacks.txt");				
			ta.RunTaintAnalysis("C:\\Users\\Fresno Bob\\Desktop\\Iterative Taint Analysis\\Test APKs\\boombeach.apk", ".\\Definitions\\SourcesAndSinks.txt");
			
			//Retrieve callgraphs for original and versioned applications.
			var cga = new CallGraphAnalysis(".\\Android Platform\\android.jar", ".\\Definitions\\AndroidCallbacks.txt");	
			var originalAPKCallGraph = cga.ReturnCallGraphForAPK("C:\\Users\\Fresno Bob\\Desktop\\Iterative Taint Analysis\\Test APKs\\boombeach.apk");
			var versionedAPKCallGraph = cga.ReturnCallGraphForAPK("C:\\Users\\Fresno Bob\\Desktop\\Iterative Taint Analysis\\Test APKs\\boombeach.apk");
			
			//Pass callgraphs to method that will perform an impact analysis determining which methods have changed, and re-run analysis based on this list.
			var impactAnalyzer = new ImpactAnalysis();		
			var methodDifferences = impactAnalyzer.PerformImpactAnalysis(originalAPKCallGraph, versionedAPKCallGraph);
			var test = "e";
			
		} 
		catch (Exception e)
		{			
			e.printStackTrace();
		};
	}
	
	

	

	/*
	 * 
	 * public static void waitForEnter() { Console c = System.console(); if (c !=
	 * null) {
	 * 
	 * c.format("\nPress enter to continue.\n"); try{ c.readLine(); }
	 * catch(Exception e){e.printStackTrace();}; c.readLine(); } }
	 * 
	 * while (clsIt.hasNext()) { SootClass sClass = (SootClass) clsIt.next(); if
	 * (sClass.isPhantom()) { // skip phantom classes continue; } // if (
	 * !sClass.isApplicationClass() ) { // skip library classes // continue; // }
	 * 
	 * // if (sClass.isInterface()) continue; // if (sClass.isInnerClass())
	 * continue;
	 * 
	 * Iterator<SootMethod> meIt = sClass.getMethods().iterator(); while
	 * (meIt.hasNext()) { SootMethod sMethod = (SootMethod) meIt.next(); if
	 * (!sMethod.isConcrete()) { // skip abstract methods and phantom methods, and
	 * native methods as well continue; } if
	 * (sMethod.toString().indexOf(": java.lang.Class class$") != -1) { // don't
	 * handle reflections now either continue; }
	 * 
	 * 
	 * Body body = sMethod.retrieveActiveBody();
	 * 
	 * 
	 * PatchingChain<Unit> pchn = body.getUnits();
	 * 
	 * 
	 * Iterator<Unit> uiter = pchn.snapshotIterator();
	 * 
	 * 
	 * while (uiter.hasNext()) {
	 * 
	 * Stmt s = (Stmt) uiter.next();
	 * 
	 * List<Object> intentProbes = new ArrayList<Object>();
	 * 
	 * 
	 * if (s.containsInvokeExpr()) {
	 * 
	 * 
	 * InvokeExpr inv = s.getInvokeExpr();
	 * 
	 * String compare = s.toString();
	 * 
	 * 
	 * boolean PrintOnce = true;
	 * 
	 * 
	 * for (int idx = 0; idx < inv.getArgCount(); idx++) {
	 * 
	 * Value curarg = inv.getArg(idx);
	 * 
	 * 
	 * } //<if contains invoke }
	 * 
	 * }
	 * 
	 * // } } //<===statement loop
	 * 
	 * 
	 * 
	 * 
	 * waitForEnter();
	 * 
	 * } // <== method
	 * 
	 * Options.v().set_src_prec(Options.src_prec_apk);
	 * Options.v().set_process_dir(Collections.singletonList(
	 * "C:\\Users\\Sabatu\\Desktop\\testAPK\\youtube.apk"));
	 * Options.v().set_android_jars(
	 * "C:\\Users\\Sabatu\\Desktop\\resources\\platforms");
	 * Options.v().set_whole_program(true);
	 * Options.v().set_allow_phantom_refs(true);
	 * Options.v().set_output_format(Options.output_format_none);
	 * Options.v().setPhaseOption("cg.spark", "on");
	 * Scene.v().loadNecessaryClasses();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * // config.setCodeEliminationMode(CEM.NoCodeElimination);
	 * //analyzer.calculateSourcesSinksEntrypoints(Collections.<AndroidMethod>
	 * emptySet(), Collections.<AndroidMethod>emptySet()); //
	 * analyzer.setConfig(config);
	 * 
	 * 
	 * // InfoflowConfiguration infoConfig = new InfoflowConfiguration();
	 * 
	 * 
	 * 
	 * 
	 * // infoConfig.setTaintAnalysisEnabled(true);
	 * 
	 * // infoConfig.setInspectSinks(true); // infoConfig.setInspectSources(false);
	 * // infoConfig.setFlowSensitiveAliasing(false); //
	 * infoConfig.setCallgraphAlgorithm(InfoflowConfiguration.CallgraphAlgorithm.
	 * AutomaticSelection);
	 * 
	 * // ISourceSinkManager sourcesink;
	 * 
	 * 
	 * 
	 * 
	 * // Infoflow info = new Infoflow();
	 * 
	 * //info.setConfig(infoConfig);
	 * 
	 * 
	 */

	/*
	 * for (Iterator<Edge> edgesIn = Scene.v().getCallGraph().edgesInto(sMethod);
	 * edgesIn.hasNext(); ){
	 * 
	 * Edge edgein = edgesIn.next();
	 * 
	 * SootMethod smSrc = edgein.src(); SootMethod smDest = edgein.tgt();
	 * 
	 * SootClass edgeSourceClass = smSrc.getDeclaringClass(); SootClass
	 * edgeDestClass = smDest.getDeclaringClass();
	 * 
	 * 
	 * 
	 * String componentTypeSource =
	 * FindComponentType.getComponentTypeActive(edgeSourceClass); String
	 * componentTypeDest = FindComponentType.getComponentTypeActive(edgeDestClass);
	 * 
	 * System.out.print("This is an edge: " + edgein.toString() + "\n");
	 * System.out.print("Here's the edges source: " + componentTypeSource + "\n");
	 * System.out.print("Here's the edges destination: " + componentTypeDest +
	 * newLine);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	// Scene.v().releaseCallGraph();

}// <===Callgraph2 Class

/*
 * 
 * for (Iterator<Edge> edgeIt = Scene.v().getCallGraph().iterator();
 * edgeIt.hasNext(); ) {
 * 
 * Edge edge = edgeIt.next();
 * 
 * SootMethod smSrc = edge.src(); Unit uSrc = edge.srcStmt(); SootMethod smDest
 * = edge.tgt();
 * 
 * SootClass edgeSourceClass = smSrc.getDeclaringClass();
 * 
 * SootClass edgeSourceDestination = smDest.getDeclaringClass();
 * 
 * String edgeSource =
 * FindComponentType.getComponentTypeActive(edgeSourceClass);
 * 
 * String edgeDestination =
 * FindComponentType.getComponentTypeActive(edgeSourceDestination);
 * 
 * //String newLine = System.getProperty("line.separator");
 * 
 * if(edgeSource != edgeDestination){
 * 
 * iccCount++;
 * 
 * //System.out.print("ICC found! \n Between: " + smSrc.toString() +
 * "(Of component type): " + edgeSource + "\n and: " + smDest.toString() +
 * " (Of component type): " + edgeDestination + " \n Found in: " +
 * uSrc.toString()); //System.out.println(newLine);
 * 
 * }
 * 
 * edgeAnalysisCount++;
 * 
 * 
 * } // <== for loop for callgraph iteration
 * 
 */

//System.out.print("\n" + iccCount + " ICC calls found, out of " + edgeAnalysisCount + " total." );

/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * List sendingArgs = new ArrayList();
 * 
 * sendingArgs.add(utilities.makeBoxedValue(sMethod, curarg, intentProbes));
 * 
 * Local foundYou2 = (Local)utilities.makeBoxedValue(sMethod, curarg,
 * intentProbes);
 * 
 * System.out.println("To string is: "+ foundYou2.toString() + "The name is: " +
 * foundYou2.getName().toString());
 * 
 * // Stmt sitnCall = Jimple.v().newInvokeStmt(
 * Jimple.v().newStaticInvokeExpr(mIntentDump.makeRef(), foundYou2));
 * 
 * 
 * 
 * 
 * 
 * 
 * String matchMe = curarg.getType().toString();
 * 
 * 
 * 
 * 
 * 
 * if(matchMe == "android.content.Intent") { Intent foundYou = null;
 * 
 * foundYou = (Intent)curarg;
 * 
 * 
 * System.out.println("Intent found. Printing relevant information:");
 * 
 * 
 * System.out.println("Here's its toString method: " + foundYou.toString());
 * 
 * System.out.println("Here's the action: " + foundYou.getAction());
 * 
 * System.out.println("Here's the type: " + foundYou.getType());
 * 
 * if (!foundYou.getCategories().isEmpty()) { Iterator<String> cats =
 * foundYou.getCategories().iterator(); int i = 1; while (cats.hasNext()) {
 * System.out.println("Here's category number " + i + ": " + cats.next()); } }
 * 
 * 
 * System.out.println(":::::::::::::::::::::::"); }
 * 
 * else {System.out.println("The type equals: " + curarg.getType().toString());
 * }
 * 
 */

/*
 * if ( compare.contains("android.intent.action") ||
 * compare.contains("android.content.Intent")) {
 * 
 * List<Object> itnProbes = new ArrayList<Object>(); List intentArgs = new
 * ArrayList();
 * 
 * //produced Soot Value, thats gets passed to helper function Value a =
 * utilities.makeBoxedValue(sMethod, curarg, itnProbes);
 * 
 * if(!itnProbes.isEmpty()) { //Produces native java.lang.object variable...
 * Iterator<Object> b = itnProbes.iterator();
 * 
 * Object c = (Object)b.next();
 * 
 * //System.out.println("Curarg is: " + curarg.toString());
 * 
 * //System.out.println("Invoked Statement is: " + inv.toString());
 * 
 * //System.out.println("Original Statement was: " + s.toString());
 * 
 * // System.out.println("Heres what it thinks this thing is: " + c.toString() +
 * " and " + a.toString() + " plus its type: " + a.getType());
 * 
 * // System.out.println("Cur");
 * 
 * 
 * 
 * List<ValueBox> ab = s.getDefBoxes(); Iterator<ValueBox> bc = ab.iterator();
 * 
 * while(bc.hasNext()) { ValueBox cd = bc.next();
 * 
 * // System.out.println("Box the statement points to: " +
 * cd.getValue().getType().toString()); }
 * 
 * 
 * 
 * List<ValueBox> d = a.getUseBoxes(); Iterator<ValueBox> e = d.listIterator();
 * 
 * while(e.hasNext()) { ValueBox f = e.next();
 * 
 * //System.out.println("The value is: " + f.getValue().toString());
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * } } PrintOnce = false;
 * 
 */
