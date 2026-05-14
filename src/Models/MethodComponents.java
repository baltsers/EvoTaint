package Models;

import java.util.ArrayList;

import soot.Body;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class MethodComponents
{
	//https://www.sable.mcgill.ca/soot/doc/soot/SootMethod.html
	public SootMethod methodSootProperties;	
	public ArrayList<Edge> edgesIntoMethod;	
	public ArrayList<Edge> edgesOutOfMethod;
	
	
	public MethodComponents()
	{
		edgesIntoMethod = new ArrayList<Edge>();
		edgesOutOfMethod = new ArrayList<Edge>();
	}
	
	public MethodComponents(SootMethod sootProperties, CallGraph sootCallGraph)
	{
		//Instantiate Array 
		this();
		
		methodSootProperties = sootProperties;		
		
		//Build iterators for all of the edges leading into and out of this method.
		var edgesLeadingIntoThisMethod = sootCallGraph.edgesInto(methodSootProperties);	
		var edgesLeadingAwayFromThisMethod = sootCallGraph.edgesOutOf(methodSootProperties);
		
		edgesLeadingIntoThisMethod.forEachRemaining(this.edgesIntoMethod::add);				
		edgesLeadingAwayFromThisMethod.forEachRemaining(this.edgesOutOfMethod::add);		
	}
	
	public MethodComponents(SootMethod sootProperties, ArrayList<Edge> edgesIn, ArrayList<Edge> edgesOut)
	{
		this();			
		methodSootProperties = sootProperties;
		edgesIntoMethod = edgesIn;
		edgesOutOfMethod = edgesOut;
	}
	
	//Compare two ArrayList<Edge> in order to determine if they are identical. Returns true if both lists are identical, and false if they are not.
	public boolean AreEdgesIdentical(ArrayList<Edge> nativeEdges, ArrayList<Edge> comparisonEdges)
	{		
		//If the number of edges vary, then the two lists are not identical.
		if(nativeEdges.size() != comparisonEdges.size())
		{
			return false;
		}		
		
		//Find a match for each edge between the two lists.  
		for(Edge nativeEdge : nativeEdges)
		{
			Edge matchedEdge = comparisonEdges.stream()
					  .filter((comparisonEdge) -> comparisonEdge.equals(nativeEdge))
					  .findFirst()
					  .orElse(null);
			
			//No match found. Return false.
			if(matchedEdge == null)
			{
				return false; 
			}
		}		
		
		return true;
	} 
	
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also  returns false */
        if (!(o instanceof MethodComponents)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        MethodComponents comparisonMethod = (MethodComponents) o;         
        
        //Compare soot properties for each method.
        if(!this.methodSootProperties.equals(comparisonMethod.methodSootProperties))
        {
        	return false;
        } 
          
        //Compare edges going into each method.
        if(!this.AreEdgesIdentical(this.edgesIntoMethod, comparisonMethod.edgesIntoMethod))
        {
        	return false;
        }
        
        //Compare edges leaving each method.
        if(!this.AreEdgesIdentical(this.edgesOutOfMethod, comparisonMethod.edgesOutOfMethod))
        {
        	return false;
        }   
        
        return true; 
    } 
} 
 
	
	

	

