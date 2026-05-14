package Analysis;
import java.util.ArrayList;
import java.util.List;

import soot.MethodOrMethodContext;
import Models.MethodComponents;
import soot.jimple.toolkits.callgraph.CallGraph;

public class ImpactAnalysis 
{
	public ImpactAnalysis()
	{
		
	}
	
	public List<MethodOrMethodContext> PerformImpactAnalysis(CallGraph originalAPKCallGraph, CallGraph versionedAPKCallGraph)
	{
		var originalMethodsIterator = originalAPKCallGraph.sourceMethods();
		List<MethodOrMethodContext> originalMethods = new ArrayList<>();
		originalMethodsIterator.forEachRemaining(originalMethods::add);			
		List<MethodOrMethodContext> changedMethods = new ArrayList<>();
		
		var versionedMethods = versionedAPKCallGraph.sourceMethods();
		
		while(versionedMethods.hasNext())
		{
			var versionedMethod = versionedMethods.next();		
			
			//Determine if method contains a match in the original. If not, re-analyze method.			
			MethodOrMethodContext originalMatchedMethod = originalMethods.stream()
					  .filter((originalMethod) -> originalMethod.method().getName() == versionedMethod.method().getName())
					  .findFirst()
					  .orElse(null);			

			//If no name match is found, re-analyze method.
			if(originalMatchedMethod == null)
			{
				changedMethods.add(versionedMethod);
				continue;
			}
			
			//Convert SootMethod to MethodComponents.
			var originalMethodComponents = new MethodComponents(originalMatchedMethod.method(), originalAPKCallGraph);
			var versionedMethodComponents = new MethodComponents(versionedMethod.method(), versionedAPKCallGraph);
			
			//Perform overriden equals check. If any differences are detected, recompute method.
			if(!originalMethodComponents.equals(versionedMethodComponents))
			{
				changedMethods.add(versionedMethod);
				continue;
			}			
			
			var test = 1;
		}
		
		return changedMethods;
	}
}
