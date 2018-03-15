package app;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import dataHandler.CSVParser;
import dataHandler.CSVWriter;
import dataHandler.sorting;
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
	public static void main(String[] args) {
		parser = new CSVParser();
		Thread parse = new Thread(parser);
		parse.setDaemon(true);
		parse.start();
//		ArrayList<Violation> v = new ArrayList<>();
//		v.add(new Violation("12/10/12", "12:23:40", "MCP", new String[] {"24.22", "52.24"}, "Injury"));
//		System.out.println(0);
//		CSVWriter c = new CSVWriter(v);
//		Thread cp = new Thread(c);
//		cp.setDaemon(true);
//		cp.start();
		launch(args);
		
		//gets the arraylist once parsing is done
		try {
			data = parser.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 parser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				
				sorting s = new sorting(data);
				
			}
		});
		 
		//call sorting class here 
		 
		
	}
	@Override
	public void start(Stage mainStage) throws Exception {
		
		
		
		
		// TODO Auto-generated method stub
	//	parser = new CSVParser();
		window = mainStage;
		window.setTitle("App title");
		
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
		
		pb.setPrefSize(150, 10);
		b.setCenter(pb);
		b.setPrefSize(300, 200);
		Scene progressS = new Scene(b);
		progressS.getStylesheets().add("App.css");
		return progressS;
	}

}