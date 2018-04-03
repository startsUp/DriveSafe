package graphing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import traffic.Violation;

public class Graph {

	private List<Vertex> graph;
	private Map<String, Vertex> hash;

//	public List<Vertex> getGraph() {
//		return graph;
//	}

	public void setGraph(List<Vertex> graph) {
		this.graph = graph;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return graph.toString();
	}
	
	public static void addViolationWeight(Vertex[] graph, HashMap<String, Integer> weights)
	
	{
		for(Vertex v: graph)
		{
			String key = Double.toString(v.getLa()) + Double.toString(v.getLo()); 
			if (weights.containsKey(key))
				System.out.println("found");
		}
	}
	
}
