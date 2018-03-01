package dataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;

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
			fr 	   = new FileReader(file);
			reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			while(line != null) {
				//do
				
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//download file from https://data.montgomerycountymd.gov/api/views/4mse-ku6q/rows.csv?accessType=DOWNLOAD
			
		}
		
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		
			
		System.out.println();
	}
}
