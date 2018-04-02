package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HandleSort {
	
	public static boolean isDataSorted(String statusFile) {
		FileReader file;
		
		try {
	
			file = new FileReader(statusFile);
			BufferedReader reader = new BufferedReader(file);
			String sortedInfo = reader.readLine().split(",")[0];
			reader.close();
			return (sortedInfo.equals("sorted:true"));			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static String getCurrentStatus(String statusFile) {
		FileReader file;
		
		try {
	
			file = new FileReader(statusFile);
			BufferedReader reader = new BufferedReader(file);
			String sortedInfo = reader.readLine();
			reader.close();
			return sortedInfo;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void updateStatus(String datasetStatus, boolean isSorted) {
		List<String> lines = new ArrayList<>();
		String lstUpdated = getCurrentStatus(datasetStatus).split(",")[1];
		if(isSorted)
			lines.add("sorted:true,"+lstUpdated);
		else 
			lines.add("sorted:false,"+lstUpdated);
		
		try {
			Files.write(Paths.get(datasetStatus), lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	
}
