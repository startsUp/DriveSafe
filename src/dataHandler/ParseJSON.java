package dataHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graphing.Vertex;

public class ParseJSON {

	private Vertex[] graph;

	public ParseJSON() {
		
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		try {
			String data = new String(Files.readAllBytes(Paths.get("data/graph.json")));
			//System.out.println(data);
			long st = System.currentTimeMillis();
			this.graph = g.fromJson(data, Vertex[].class);
			System.out.println(System.currentTimeMillis() - st);
			System.out.println(graph);
			System.out.println(graph.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ParseJSON p = new ParseJSON();
	}
	
	public Vertex[] getVertices()
	{
		return this.graph;
	}
	
}

