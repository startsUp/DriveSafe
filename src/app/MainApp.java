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
	private static CSVParser parser;
	public static void main(String[] args) {
		parser = new CSVParser();
		Thread parse = new Thread(parser);
		parse.setDaemon(true);
		parse.start();
		
		launch(args);
		//start loading data here
		
		
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
