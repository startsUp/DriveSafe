package graphing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

	private Vertex[] vertices;
	private HashMap<String, List<Integer>> map;
	private String[] keys;

	//	public List<Vertex> getGraph() {
	//		return graph;
	//	}

	public Graph(Vertex[] vertices) {
		this.vertices = vertices;
		map = new HashMap<>();
		keys = new String[V()];
		fillMap(vertices);
	}

	private void fillMap(Vertex[] vertices) {
		for(int i=0; i<V(); i++)
		{
			Vertex v = vertices[i];
			String key="";
			try {
				key = Double.toString(v.getLa()).substring(0, 6) + Double.toString(v.getLo()).substring(0, 7); 

			} catch (Exception e) {
				continue;
			}	
			if(map.containsKey(key))
				map.get(key).add(i);
			else {
				List<Integer> list = new ArrayList<>();
				list.add(i);
				map.put(key, list);
			}
				
			
		}

	}

	public int V()
	{
		return vertices.length;
	}

	public Vertex getVertex(int i)
	{
		return vertices[i];
	}

	public static void addViolationWeight(Graph graph, HashMap<String, Integer> weights)

	{
		int found= 0;
		HashMap<String, Boolean> updatedEdges = new HashMap<>();
		for(int i=0; i<graph.V(); i++)
		{
			Vertex v = graph.getVertex(i);

			String key="";
			try {
				key = Double.toString(v.getLa()).substring(0, 6) + Double.toString(v.getLo()).substring(0, 7); 

			} catch (Exception e) {
				System.out.println("Failed : " + v.getLa() + " " + v.getLo());
				continue;
			}	


			for(int j = 0; j < v.getE().size(); j++)
			{
				v.getE().get(j).setFrom(i);
				if (weights.containsKey(key))
				{
					int violationCount = weights.get(key);

					Edge e = v.getE().get(j);
					int to = e.getI();
					String edgeKey = Integer.toString(i)+to;
					if(updatedEdges.containsKey(edgeKey))
					{
						updatedEdges.put(edgeKey, true);

						v.getE().set(j, new Edge(e.getI(), updateWeight(e, violationCount)));
					}

				}
			}


		}
		System.out.println("found : " + found);
	}

	private static int updateWeight(Edge e, int violationCount) {
		// TODO Auto-generated method stub
		return e.getW()+violationCount;
	}

	public int getVertexID(String lat, String lng)
	{
		double lt = Double.parseDouble(lat);
		double ln = Double.parseDouble(lng);
		System.out.println("key" + lat + " " + lng);
		if(lt<36 || lt>40 || ln <-79 || ln>-75) return -1; //out of data bounds

		String latN = lat.substring(0, 6);
		String lngN = lng.substring(0, 7);
		String key  = latN+lngN;
		System.out.println("key" + key);
		int closest = -1;
		double minDist = Double.MAX_VALUE;
		if(map.containsKey(key))
		{
			for(int i: map.get(key))
			{
				double distance = distance(lt, ln, vertices[i].getLa(), vertices[i].getLo());
				if(distance<minDist)
				{
					minDist=distance;
					closest = i;
				}
					
				
			}
		}

		return closest;
	}


	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);

		dist = dist * 1.609344;

		return (dist);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
}
