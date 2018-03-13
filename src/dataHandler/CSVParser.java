package dataHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import javafx.concurrent.Task;
import traffic.Violation;

/**
 * 
 * @author shardool
 *
 * This class Parses a CSV File and returns the ArrayList of the data
 *
 */
public class CSVParser extends Task<ArrayList<Violation>> {
//add test case for this class 
	private final static String file = "data/Traffic_Violations.csv";
	private final static double MAX_EST_PROGRESS = 1200000; //estimated valid lines
	

	public static void main(String[] args) throws IOException {

		
		CSVParser parser = new CSVParser();
		//this starts the task of parsing the data
		new Thread(parser).start();
		
		//to get the array list you call parser.get() 
//		final FileChannel channel = new FileInputStream(file).getChannel();
//		MappedByteBuffer b = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//		
//		channel.close();
//		
	}

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
									   new double[] {Double.parseDouble(lineData[6]),
											   		 Double.parseDouble(lineData[7])},
									   lineData[4])
				);
			
				
				line = reader.readLine();
				
				//MAX_EST_PROGRESS can be made more precise by reading the file and every time the application is ran, the 
				//number of lines read is stored into a file. 
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
		return data;
	
	}
}
