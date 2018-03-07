package dataHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import traffic.Violation;

/**
 * 
 * @author shardool
 *
 * This class Parses a csv File and stores the data into a hashmap
 *
 */
public class ParseCSVFile {


	protected static ArrayList<Violation> parseCSV(String file) {

		ArrayList<Violation> data = new ArrayList<>();

		FileReader fr;
		BufferedReader reader;
		String line;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			line = reader.readLine();
			line = reader.readLine();
			
			while(line != null) {
				//do
				
				if(line.length()<100) {
					String nextLine = reader.readLine();
					if(nextLine != null)
						line = line+nextLine;
				}

				String[] lineData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				
				//skip line if no geolocation information is available
				if(lineData[6].isEmpty() || lineData[7].isEmpty()) {
					line = reader.readLine();
					continue;
				}
				long st = System.currentTimeMillis();
				
				data.add(new Violation(lineData[0], lineData[1], lineData[2], new double[] {Double.parseDouble(lineData[6]),
						Double.parseDouble(lineData[7])}, lineData[4]));
			
				
				line = reader.readLine();
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println(line);
			e.printStackTrace();

			//download file from https://data.montgomerycountymd.gov/api/views/4mse-ku6q/rows.csv?accessType=DOWNLOAD

		}

		System.out.println(data.get(data.size()-1));
		return data;

	}

	public static void main(String[] args) {

		//long st = System.currentTimeMillis();
		ParseCSVFile.parseCSV("data/Traffic_Violations.csv");
		//System.out.println((System.currentTimeMillis()-st)/1000); //~25 secs to pars
		
		System.out.println();
	}
}
