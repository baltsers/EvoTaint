package Configuration;

import soot.jimple.infoflow.InfoflowConfiguration.CodeEliminationMode;
import soot.jimple.infoflow.android.SetupApplication;

public class SootConfiguration
{
	String androidPlatformFilePath;
	String androidCallbacksFilePath;
	
	public SootConfiguration(String androidFilePath, String androidCBFilePath)
	{
		androidPlatformFilePath = androidFilePath;
		androidCallbacksFilePath = androidCBFilePath;
	}
	
	//This method configures how the analysis will be performed.	
	public SetupApplication BuildAnalysisConfiguration(String apkFilePath, boolean runTaintAnalysis)
	{		
		var analyzer = new SetupApplication(this.androidPlatformFilePath, apkFilePath);			
				
		analyzer.setCallbackFile(this.androidCallbacksFilePath);	
		(analyzer.getConfig()).setTaintAnalysisEnabled(runTaintAnalysis);
		(analyzer.getConfig()).printSummary();
	
		// No idea.
		(analyzer.getConfig()).setCodeEliminationMode(CodeEliminationMode.NoCodeElimination);

		// No idea.
		// (analyzer.getConfig()).setImplicitFlowMode(InfoflowConfiguration.ImplicitFlowMode.NoImplicitFlows);

		(analyzer.getConfig()).setFlowSensitiveAliasing(false);
		(analyzer.getConfig()).setInspectSinks(true);
		(analyzer.getConfig()).setInspectSources(true);	
		
		return analyzer;
	}	

}
