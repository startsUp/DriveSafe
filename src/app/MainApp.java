package app;

import dataHandler.CSVParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import dataHandler.*;
//import traffic.*;

public class MainApp extends Application {
	
	private Stage window;
	private Scene startUpScene;
	private CSVParser parser;
	public static void main(String[] args) {
		launch(args);
		//start loading data here
		
		
	}
	@Override
	public void start(Stage mainStage) throws Exception {
		
		// TODO Auto-generated method stub
		parser = new CSVParser();
		window = mainStage;
		window.setTitle("App title");
		
		startUpScene = getStartUpScreen();
		
		
		window.setScene(startUpScene);
		window.show();
		window.setMaximized(true);
		
	}
	
	private Scene getStartUpScreen() {
		BorderPane b = new BorderPane();
		final ProgressBar pb = new ProgressBar(0);
		
		CSVParser parser = new CSVParser();
		new Thread(parser).start();
		

		pb.progressProperty().unbind();
		pb.progressProperty().bind(parser.progressProperty());
		
		
		b.setCenter(pb);
		return new Scene(b);
	}

}
