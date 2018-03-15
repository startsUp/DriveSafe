package dataHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;

/**
 * This class parses the CSV file: Traffic_Violation.csv under the data directory. It stores the
 * data into an ArrayList of Violation ADT. This class extends the class Task<?> and therefore has the ability
 * to run in the background without blocking UI updates.
 * @author shardool
 */
public class CSVParser extends Task<ArrayList<Violation>> {
	

	private final static String file = "data/Traffic_Violations.csv";
	private final static double MAX_EST_PROGRESS = 1190000; //estimated valid lines
	

	public static void main(String[] args) throws IOException {

		
//		CSVParser parser = new CSVParser();
//		//this starts the task of parsing the data
//		new Thread(parser).start();
		
		
	}

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
		
		FileReader fr;
		BufferedReader reader;
		String line;
		int progress = 0;
		
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			line = reader.readLine();
			line = reader.readLine();
			
			while(line != null) {
				//do
				if(isCancelled()) {
					updateMessage("Cancelled");
					break;
				}
				if(line.length()<100) {
					String nextLine = reader.readLine();
					if(nextLine != null)
						line = line+nextLine;
				}

				String[] lineData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				
				//skip line if no geo-location information is available
				if(lineData[6].isEmpty() || lineData[7].isEmpty()) {
					line = reader.readLine();
					continue;
				}
			
				
				data.add(new Violation(lineData[0],
									   lineData[1],
									   lineData[2],
									   new String[] {(lineData[6]),
											   		 (lineData[7])},
									   lineData[4],
									   lineData[5],
									   lineData[18])
				);
			
				
				line = reader.readLine();
				
				//MAX_EST_PROGRESS can be made more precise by reading the file and every time the application is ran, the 
				//number of lines read is stored into a file. 
				//System.out.println(progress);
				this.updateProgress(progress++, MAX_EST_PROGRESS);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println(line);
			e.printStackTrace();

			

		}
		this.updateProgress(MAX_EST_PROGRESS, MAX_EST_PROGRESS);
		this.updateMessage("File Read");
		System.out.println(data.get(data.size()-1));
		this.succeeded();
		return data;
	
	}
	
	
}
