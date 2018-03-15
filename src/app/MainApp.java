package app;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import dataHandler.CSVParser;
import dataHandler.CSVWriter;
import dataHandler.SortList;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import traffic.Violation;
/**
 * This class is the Main entry point of the application. It provides a GUI using JavaFX.
 * @author shardool
 */
public class MainApp extends Application {
	
	private Stage window;
	private Scene startUpScene;
	private static CSVParser parser;
	private static CSVWriter writer;
	private static ArrayList<Violation> data;
	
	
	public static void main(String[] args) {
		
		parser = new CSVParser();
		writer = new CSVWriter(data);
		
		Thread parse = new Thread(parser);
		Thread write = new Thread(writer);
		
		write.setDaemon(true); // keeps running even after JVM exits
		parse.setDaemon(true);
		
		
		parse.start(); //starts parsing data

		launch(args);
			 	
	}
	
	
	@Override
	public void start(Stage mainStage) throws Exception {
		
		
		window = mainStage;
		window.setTitle("App title");
		
		startUpScene = getStartUpScreen();
		
		
		window.setScene(startUpScene);
		window.show();
		window.sizeToScene();
		
		//once parsing is done store the data globally and sort it
		 parser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				try {
					data = parser.get();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SortList.sort(data);
				//start writing the sorted data into the existing file
			}
		});
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