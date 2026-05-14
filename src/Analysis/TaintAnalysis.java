package Analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import Configuration.SootConfiguration;
import Models.MethodComponents;
import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.infoflow.InfoflowConfiguration.CodeEliminationMode;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;


//Rough idea: determine which methods have changed, and trace these back to the source - sink paths already computed.
//Re-run second analysis using reduced set of sources-sinks based on what actually changed.
public class TaintAnalysis
{
	SootConfiguration sootConfig;
	
	public TaintAnalysis(String androidFilePath, String androidCBFilePath)
	{
		sootConfig = new SootConfiguration(androidFilePath, androidCBFilePath);
	}
	
	public void RunTaintAnalysis(String apkFilePath, String SourcesAndSinksFilePath) throws IOException, XmlPullParserException 
	{			
		// Reset soot for some reason..
		soot.G.reset();
		
		var analyzer = sootConfig.BuildAnalysisConfiguration(apkFilePath, true);

		InfoflowResults resultMap = analyzer.runInfoflow(SourcesAndSinksFilePath);			
		Writer writer = new BufferedWriter(new FileWriter(".\\TaintAnalysisResults.txt"));			
		resultMap.printResults(writer);			
		writer.close();
	}
	
	public void ParseSootClasses()
	{
		Iterator<SootClass> clsIt = (Scene.v().getClasses().snapshotIterator());	
	}
}