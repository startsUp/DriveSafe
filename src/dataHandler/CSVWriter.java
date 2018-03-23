package dataHandler;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import app.MainApp;
import javafx.concurrent.Task;
import traffic.Violation;


/**
 * This class writes the sorted data to the CSV file: Traffic_Violation.csv under the data directory.
 * This class extends the class Task<?> and therefore has the ability to run in the background without blocking UI updates.
 * @author shardool
 */
public class CSVWriter extends Task<Void>{
	
	private ArrayList<Violation> data;
	
	
	public CSVWriter(ArrayList<Violation> data) {
		this.data = data;
	}
	
	@Override
	protected Void call() throws Exception {
		//write data into Traffic_Violation.csv
		
		try {
		
			ArrayList<String> lines = new ArrayList<>();
			for(Violation v: data)
				lines.add(v.csvFormat());
			
			Files.write(Paths.get(MainApp.DATASET), lines, Charset.forName("UTF-8"));	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
