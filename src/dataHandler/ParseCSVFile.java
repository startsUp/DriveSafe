package dataHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 
 * @author 
 *
 * This class Parses a csv File and stores the data into a hashmap
 *
 */
public class ParseCSVFile {

	
	protected static void parseCSV(String file, HashMap<Comparable<?>, Object> data) {
		
		FileReader fr;
		BufferedReader reader;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			while(line != null) {
				//do
				String[] lineData = line.split(",");
				
				//TO DO - Make DateAndTime Class with following method
				//key = DateandTime.parse(Date, Time) returns a string/comparable object such that date and time is concatenated to form a unique key
				//System.out.println(lineData);
				
				//Violation v = new Violation(dateOfViolation, timeOfStop, agency, latlong, description, fatal, violationType, dlState);
				// store required fields into hashmap
				
				line = reader.readLine();
				break;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//download file from https://data.montgomerycountymd.gov/api/views/4mse-ku6q/rows.csv?accessType=DOWNLOAD
			
		}
		
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		
		ParseCSVFile.parseCSV("data/Traffic_Violations.csv", new HashMap<>());	
		System.out.println();
	}
}
