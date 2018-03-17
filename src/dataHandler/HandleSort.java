package dataHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	
	

	
}
