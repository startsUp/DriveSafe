package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import dataHandler.*;
//import traffic.*;

public class MainApp extends Application {
	
	private Stage window;
	private Scene startUpScene;
	public static void main(String[] args) {
		launch(args);
		//start loading data here
		
		
	}
	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		window = mainStage;
		window.setTitle("App title");
		
		startUpScene = getStartUpScreen();
		
		
		window.setScene(startUpScene);
		window.show();
		window.setMaximized(true);
		
	}
	
	private Scene getStartUpScreen() {
		BorderPane b = new BorderPane();
		return new Scene(b);
	}

}
