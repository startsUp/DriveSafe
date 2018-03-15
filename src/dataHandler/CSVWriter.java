package dataHandler;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;


/**
 * This class writes the sorted data to the CSV file: Traffic_Violation.csv under the data directory.
 * This class extends the class Task<?> and therefore has the ability to run in the background without blocking UI updates.
 * @author shardool
 */
public class CSVWriter extends Task<Void>{
	
	private ArrayList<Violation> data;
	private final static String file = "data/Traffic_V.csv";
	
	public CSVWriter(ArrayList<Violation> data) {
		this.data = data;
	}
	@Override
	protected Void call() throws Exception {
		//write data into Traffic_Violation.csv
		
		try {
			FileWriter fwr = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fwr);
			writer.write(data.get(0).csvFormat()); // do this for all lines
			writer.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}

}
