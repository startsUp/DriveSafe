package app;

import java.util.ArrayList;

import dataHandler.CSVParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sorting.HandleSort;
import sorting.SortList;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

public class MainApp extends Application {

	private Stage window;
	private Scene startUpScene;

	public final static String DATASET = "data/Traffic_Violations.csv"; 
	public final static String DATASET_STATUS = "data/D_status.txt"; 
	
	private static CSVParser parser;
	private static Thread parsingThread;
	private static ArrayList<Violation> data;
	private static ProgressBar parsingProgress;

	public static void main(String[] args) throws Exception{

		
		//start parser in the background as soon as App starts 
		parser = new CSVParser();
		parsingThread = new Thread(parser);
		parsingThread.setDaemon(true);
		parsingThread.start();

		launch(args);
		//gets the arraylist once parsing is done
		//call sorting class here 


	}
	@Override
	public void start(Stage mainStage) throws Exception {

		window = mainStage;
		window.setTitle("DriveSafe");
		startUpScene = getNewScene();
		window.setScene(startUpScene);
		window.show();
		window.sizeToScene();
		
		parser.setOnSucceeded(e -> {
			data = parser.getValue();
			//window.setScene(getNewScene());
			if(!HandleSort.isDataSorted(DATASET_STATUS))
				SortList.sort(data, 0);
		});
		

	}

	

	private Scene getNewScene() {


		BorderPane b = new BorderPane();
		Button bs = new Button("Map");	
		
		bs.setOnAction(e->{
			showMapScene(); 
		});
	
		//TO DO- make unit test for each class
		//SortList.sort(data, 0);
		parsingProgress = new ProgressBar(0);
		parsingProgress.progressProperty().bind(parser.progressProperty());

		parsingProgress.setPrefSize(150, 25);
		b.setTop(parsingProgress);
		b.setPrefSize(300, 200);
		
	

		
//		BoundBox bx = new BoundBox(data);
//		bx.Bounding(data, -94.6109883333333, -71.2857083333333, 37.0383713333333, 41.5120733333333);
//		System.out.println("Starting BoundBox: Complete");
		
		
		b.setCenter(bs);
		b.setPrefSize(300, 200);
		Scene map = new Scene(b);
		return map;
	}
	
	public void showMapScene() {
		
		
//		if(parser.isRunning())
//			try {
//				parse.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		Map map = new Map();
		BorderPane mapView = map.getGooogleMap(data);
		mapView.setBottom(parsingProgress);
		
		Scene s = new Scene(mapView);
		window.setScene(s);
	}

	
}

