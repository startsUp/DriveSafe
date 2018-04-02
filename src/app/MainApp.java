package app;


import java.util.ArrayList;

import dataHandler.CSVParser;
import dataHandler.CSVWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sorting.HandleSort;
import sorting.SortList;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

public class MainApp extends Application {

	private Stage window;
	private Scene startUpScene;
	private Scene loadingScene;

	private final static String DATASET = "data/Traffic_Violations.csv"; 
//	private final static String DATASET_TEST = "data/Traffic_V.csv"; 
	private final static String DATASET_STATUS = "data/D_status.txt"; 
	
	private static CSVParser parser;
	private static Thread parsingThread;
	private static Thread sortingThread;
	private static Thread writingThread;
	private static ArrayList<Violation> data;
	private static ProgressBar parsingProgress;

	public static void main(String[] args) throws Exception{

		
		//start parser in the background as soon as App starts 
		parser = new CSVParser(DATASET);
		parsingThread = new Thread(parser);
		
		parsingThread.setPriority(Thread.MAX_PRIORITY);
		parsingThread.setDaemon(true);
		parsingThread.start();

		launch(args);


	}
	@Override
	public void start(Stage mainStage) throws Exception {

		window = mainStage;

		window.setTitle("DriveSafe");
		startUpScene = getNewScene();
		getLoadingScreen();
		window.setScene(startUpScene);
		//window.show();
		window.sizeToScene();
		
		parser.setOnSucceeded(e -> {
			data = parser.getValue();
			
			//if data is not parsed, parse and update the dataset
			if(!HandleSort.isDataSorted(DATASET_STATUS))
			{
				SortList sort = new SortList(data, 0);
				sortingThread = new Thread(sort);
				sortingThread.start();
				try {
					sortingThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				parsingProgress.progressProperty().unbind();
				parsingProgress.progressProperty().bind(sort.progressProperty());
				CSVWriter writer = new CSVWriter(data, DATASET);
				writingThread = new Thread(writer);
				writingThread.start();
				HandleSort.updateStatus(DATASET_STATUS, true);
				
			}
			
			
			window.setScene(startUpScene);
			window.show();
			
			//window.setScene(getNewScene());
			
				
			System.out.println(data.get(0));
			System.out.println(data.size());
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
		map.getStylesheets().add("App.css");
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
		Button getData = new Button("Data");
		
		getData.setOnAction(e->{
			if(parser.isRunning())
				try {
	
					
					HBox s = new HBox(parsingProgress);
					
					Stage prog = new Stage();
					prog.setScene(new Scene(s));
					prog.show();
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			System.out.println(data.get(0));
		});
		
		Map map = new Map();
		BorderPane mapView = map.getGooogleMap(data);
//		mapView.setBottom(parsingProgress);
//		mapView.setTop(getData);
		Scene s = new Scene(mapView);
		window.setScene(s);
	}

	public void getLoadingScreen()
	{
		Stage s = new Stage();
		HBox g = new HBox(parsingProgress);
		s.setScene(new Scene(g));
		s.show();
		
		
	}
}

