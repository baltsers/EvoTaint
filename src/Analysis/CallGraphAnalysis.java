package Analysis;
import Configuration.SootConfiguration;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

public class CallGraphAnalysis
{	
	SootConfiguration sootConfig;
	
	public CallGraphAnalysis(String androidFilePath, String androidCBFilePath)
	{
		sootConfig = new SootConfiguration(androidFilePath, androidCBFilePath);
	}
	
	public CallGraph ReturnCallGraphForAPK(String apkFilePath)
	{		
		var analyzer = sootConfig.BuildAnalysisConfiguration(apkFilePath, false);
		analyzer.constructCallgraph();
		return Scene.v().getCallGraph();
	}	
}
