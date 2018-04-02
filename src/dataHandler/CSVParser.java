package dataHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthSpinnerUI;

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
	private String file;

	public CSVParser(String file) {
		this.file = file;
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
		final Pattern REGEX = Pattern.compile(",");

		List<String> allLines = Files.readAllLines(Paths.get(this.file));

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
			if(lineData[7].isEmpty() || lineData[8].isEmpty()) { i++; continue;}
			int ltlngIndex=7;

			do
			{
				try {
					
					double lt = Double.parseDouble(lineData[ltlngIndex]);
					double lng = Double.parseDouble(lineData[ltlngIndex+1]);
					if(Math.abs(lt)>200) {ltlngIndex++; continue;} 
				} catch (Exception e) {
					ltlngIndex++;
					//System.out.println(index);
					if(ltlngIndex>15) break;
					continue;
				}
				break;
			}while(true);
			
			if (ltlngIndex>15) { i++; continue;}
			
			data.add(new Violation(lineData[0],lineData[1],lineData[2], new String[] {(lineData[ltlngIndex]),(lineData[ltlngIndex+1])}, lineData[ltlngIndex-2],lineData[ltlngIndex-1],lineData[ltlngIndex+12]));

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
	public int yo()
	{
		return 0;
	}
	public String data(String line)
	{
		String s ="";
		int i=0;
		while(i<line.length())
		{
			
			if(line.charAt(i)!='"')
				s+=line.charAt(i++);
			else
			{
				i++;
				while(line.charAt(i)!='"')
				{
					if(line.charAt(i)!=',')
						s+=line.charAt(i++);
					else
						i++;
				}

				i++;
			}

		}
		return s;
	}

}
