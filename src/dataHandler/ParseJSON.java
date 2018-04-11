package dataHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graphing.Vertex;

public class ParseJSON {

	private Vertex[] graph;

	public ParseJSON() {
		
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		try {
			String data = new String(Files.readAllBytes(Paths.get("data/graph.json")));
			this.graph = g.fromJson(data, Vertex[].class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public Vertex[] getVertices()
	{
		return this.graph;
	}
	
}

