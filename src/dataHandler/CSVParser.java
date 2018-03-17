package dataHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import app.MainApp;
import javafx.concurrent.Task;
import traffic.Violation;

/**
 * This class parses the CSV file: Traffic_Violation.csv under the data directory. It stores the
 * data into an ArrayList of Violation ADT. This class extends the class Task<?> and therefore has the ability
 * to run in the background without blocking UI updates.
 * @author shardool
 */
public class CSVParser extends Task<ArrayList<Violation>> {
	

	private final static double MAX_EST_PROGRESS = 1190000; //estimated valid lines
	

	/**
	 * This method is inherited from the superclass Task<?> and is called whenever an instance of 
	 * CSVParser is started via: new Thread(parser).start(), where parser is an instance of CSVParser.
	 * Upon successful completion of this method another method called get() can be called using parser.get()
	 * to obtain the ArrayList that the data was stored into.
	 * @throws Exception an unhandled exception which occurred during the background operation
	 */
	@Override
	protected ArrayList<Violation> call() throws Exception {

		ArrayList<Violation> data = new ArrayList<>();
		final Pattern REGEX = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		
		List<String> allLines = Files.readAllLines(Paths.get(MainApp.DATASET));
		
		int i=1;
		int progress = 0;
		
		long st = System.currentTimeMillis();
		
		while(i<allLines.size()) {
			
			String line = allLines.get(i);
			allLines.set(i, null);
			
			if(line.length()<100 && (++i)<allLines.size()) {
				line+=allLines.get(i);
				allLines.set(i, null);
			}
			
			String[] lineData = REGEX.split(line);
			
			//skip line if no geo-location information is available
			if(lineData[6].isEmpty() || lineData[7].isEmpty()) { i++; continue;}
		
			data.add(new Violation(lineData[0],lineData[1],lineData[2], new String[] {(lineData[6]),(lineData[7])}, lineData[4],lineData[5],lineData[18]));
			
			this.updateProgress(progress++, MAX_EST_PROGRESS);
			i++;	
		}
		
		allLines = null;
		this.updateProgress(MAX_EST_PROGRESS, MAX_EST_PROGRESS);
		this.updateMessage("File Read");
		this.succeeded();
		
		System.out.println(System.currentTimeMillis()-st);
		return data;
	
	}
	
	
}
