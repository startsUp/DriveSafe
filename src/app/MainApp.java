package app;


import java.io.File;
import java.util.ArrayList;

import dataHandler.CSVParser;
import dataHandler.CSVWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sorting.HandleSort;
import sorting.SortList;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

public class MainApp extends Application {

	private Stage window;
	private Scene startUpScene;
	private Scene loadingScene;
	private Stage loadingStage;

	private final static String DATASET = "data/Traffic_Violations.csv"; 
	//	private final static String DATASET_TEST = "data/Traffic_V.csv"; 
	public final static String DATASET_STATUS = "data/D_status.txt"; 

	private static boolean isDataSorted;
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
		//	window.sizeToScene();

		parser.setOnSucceeded(e -> {
			data = parser.getValue();

			//if data is not parsed, parse and update the dataset
			if(!HandleSort.isDataSorted(DATASET_STATUS))
			{
				SortList sort = new SortList(data, 0);
				sortingThread = new Thread(sort);
				sortingThread.start();
				try 
				{
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

			loadingStage.close();
			showMapScene();
			//window.setFullScreen(true);
			window.getIcons().add(new Image("file:res/appIcon.png"));
			window.setMaximized(true);
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

		parsingProgress.setPrefSize(400, 15);
		b.setTop(parsingProgress);
		b.setPrefSize(350, 200);




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

		Map map = new Map();
		GridPane mapView = map.getGooogleMap(data);
		//		mapView.setBottom(parsingProgress);
		//		mapView.setTop(getData);
		window.setMaximized(true);
		Scene s = new Scene(mapView);

		window.setScene(s);
	}

	public void getLoadingScreen()
	{
		loadingStage = new Stage();
		loadingStage.initStyle(StageStyle.UNDECORATED);
		loadingStage.setScene(getLoadScene());
		loadingStage.sizeToScene();
		loadingStage.show();


	}

	private Scene getLoadScene()
	{
		Image image = new Image(new File("res/drivesafe.png").toURI().toString());

		GridPane p = new GridPane();
		p.getStyleClass().add("pane2");
		ImageView iv1 = new ImageView(image);
		iv1.setStyle("-fx-background-color: #7FDBFF");
		HBox g = new HBox(parsingProgress);
		g.getStyleClass().add("pane2");
		g.setPadding(new Insets(20));

		//g.relocate(0,300);
		p.setAlignment(Pos.CENTER);
		p.add(iv1, 0, 0);
		p.add(g,0,1);
		g.setAlignment(Pos.CENTER);

		//iv1.relocate(-365, -250);
		Scene scene = new Scene(p, 650, 350);

		scene.getStylesheets().add("app.css");
		return scene;

	}
}

