package app;

import java.util.ArrayList;

import dataHandler.BoundBox;
import dataHandler.CSVParser;
import dataHandler.SortList;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

public class MainApp extends Application {
	
	private Stage window;
	private Scene startUpScene;
	private static CSVParser parser;
	private static ArrayList<Violation> data;
	public static void main(String[] args) throws Exception{
		launch(args);
//		ArrayList<Violation> v = new ArrayList<>();
//		v.add(new Violation("12/10/12", "12:23:40", "MCP", new String[] {"24.22", "52.24"}, "Injury"));
//		System.out.println(0);
//		CSVWriter c = new CSVWriter(v);
//		Thread cp = new Thread(c);
//		cp.setDaemon(true);
//		cp.start();
		
		/*
		try {
			data = parser.getValue();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		data = parser.getValue();
		parser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("THIS IS IT REACHING SORTING");
				//sorting s = new sorting(data);
				sorting.sort(data, 0);
				
			}
		});
		*/
		//gets the arraylist once parsing is done
		
		 
		//call sorting class here 
		
		
	}
	@Override
	public void start(Stage mainStage) throws Exception {
		//System.out.println("Start: CSVParser()");
		parser = new CSVParser();
		Thread parse = new Thread(parser);
		parse.setDaemon(true);
		parse.start();
		System.out.println("Reading Data:");
		parser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("Reading Data: Complete");
				data = parser.getValue();
				System.out.println("Sorting Data:");
				SortList.sort(data, 0);
				System.out.println("Sorting Data: Complete");
				/*
				for(int i = 0; i <= 1000; i++){
					System.out.printf("%s:%s\n",sorted_data.get(i).getLatlong()[0],sorted_data.get(i).getLatlong()[1]);					
				}
				*/
				System.out.println("Checking isSorted:");
				for(int i = 0; i < data.size()-1; i++){
					if (!SortList.isSorted(data.get(i).getLatlong()[0],data.get(i+1).getLatlong()[0])){
						System.out.println("Checking isSorted: Failed");
					}
				}
				System.out.println("Checking isSorted: Passed!");
				
				System.out.println("Starting BoundBox");
				BoundBox b = new BoundBox(data);
				b.Bounding(data, -94.6109883333333, -71.2857083333333, 37.0383713333333, 41.5120733333333);
				System.out.println("Starting BoundBox: Complete");
			}
		});
		// TODO Auto-generated method stub
	//	parser = new CSVParser();
		window = mainStage;
		window.setTitle("DriveSafe");
		startUpScene = getStartUpScreen();
		window.setScene(startUpScene);
		window.show();
		window.sizeToScene();
		//window.setMaximized(true);
		
		
	}
	
	private Scene getStartUpScreen() {
		BorderPane b = new BorderPane();
		final ProgressBar pb = new ProgressBar(0);		
		//pb.progressProperty().unbind();
		pb.progressProperty().bind(parser.progressProperty());
		
		pb.setPrefSize(150, 25);
		b.setCenter(pb);
		b.setPrefSize(300, 200);
		Scene progressS = new Scene(b);
		progressS.getStylesheets().add("App.css");
		return progressS;
	}

}


/*
parser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	@Override
	public void handle(WorkerStateEvent event) {
		data = parser.getValue();
		System.out.println("THIS IS IT REACHING SORTING");
		sorting s = new sorting(data);
		s.sort(data, 0);
		ArrayList<Violation> sorted_data = s.getdata();
		System.out.println("THIS IS IT FINISHING THE SORTING");
		for(int i = 0; i <= 1000; i++){
			System.out.printf("%s:%s\n",sorted_data.get(i).getLatlong()[0],sorted_data.get(i).getLatlong()[1]);					
		}
		
		for(int i = 0; i < sorted_data.size()-1; i++){
			if (!sorting.isSorted(sorted_data.get(i).getLatlong()[0],sorted_data.get(i+1).getLatlong()[0])){
				System.out.println("Ooops bamboozle");
			}
		}
		System.out.println("No bamboozle");
		
		BoundBox b = new BoundBox(sorted_data);
		b.Bounding(sorted_data, -94.6109883333333, -71.2857083333333, 37.0383713333333, 41.5120733333333);
	}
});
*/
