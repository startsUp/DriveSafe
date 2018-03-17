package app;

import java.util.ArrayList;

import dataHandler.BoundBox;
import dataHandler.CSVParser;
import dataHandler.SortList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

public class MainApp extends Application {

	private Stage window;
	private Scene startUpScene;

	public final static String DATASET = "data/Traffic_Violations.csv"; 

	private static CSVParser parser;
	private static ArrayList<Violation> data;


	public static void main(String[] args) throws Exception{

		
		//start parser in the background as soon as App starts 
		parser = new CSVParser();
		Thread parse = new Thread(parser);
		parse.setDaemon(true);
		parse.start();

		launch(args);
		//gets the arraylist once parsing is done


		//call sorting class here 


	}
	@Override
	public void start(Stage mainStage) throws Exception {

		window = mainStage;
		window.setTitle("DriveSafe");
		startUpScene = getStartUpScreen();
		window.setScene(startUpScene);
		window.show();
		window.sizeToScene();
		
		parser.setOnSucceeded(e -> {
			data = parser.getValue();
			window.setScene(getNewScene());
		});
		

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

	private Scene getNewScene() {


		BorderPane b = new BorderPane();
		Button bs = new Button("Map");		
		//pb.progressProperty().unbind();
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
		BoundBox bx = new BoundBox(data);
		bx.Bounding(data, -94.6109883333333, -71.2857083333333, 37.0383713333333, 41.5120733333333);
		System.out.println("Starting BoundBox: Complete");
		b.setCenter(bs);
		b.setPrefSize(300, 200);
		Scene map = new Scene(b);
		return map;
	}

}

