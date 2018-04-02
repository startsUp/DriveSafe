package app;

import java.net.URL;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import traffic.Violation;
public class Map {


	private double bounds[];

	public BorderPane getGooogleMap(ArrayList<Violation> data){

		
		this.bounds = new double[4];
		BorderPane mapPane = new BorderPane();



		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();


		final URL googleMap = getClass().getResource("demo.html");



		ProgressBar prog = new ProgressBar(0);
		prog.setPadding(new Insets(15,0,0,44));
		prog.setPrefWidth(400);
		prog.progressProperty().unbind();
		prog.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
		webEngine.load(googleMap.toExternalForm());



		webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> e) {
				System.out.println(e.toString());
			}
		});
		
		
		webEngine.setOnStatusChanged(e->{
			System.out.println(e);
		});


		webEngine.setJavaScriptEnabled(true);
		//		reload.setOnAction(e-> webEngine.reload());
		//		rightB.getChildren().addAll(reload, add);
		//JSObject win = (JSObject) webEngine.executeScript("window");
		JavaScriptUpCall jsObj = new JavaScriptUpCall();
//		win.setMember("app", jsObj);

		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<Worker.State>() {
					public void changed(ObservableValue<? extends State> ov, Worker.State oldState, Worker.State newState) {
						if (newState == Worker.State.SUCCEEDED) {                
							System.out.println(0);
							JSObject win = (JSObject) webEngine.executeScript("window");
							win.setMember("app",  jsObj);
						}

					}
				});
		webEngine.setOnError(e -> {
			System.out.println(e);
		});



		Slider slider = new Slider();
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);

	
	
		
		slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasChanging, Boolean isNowChanging) {
		        if (!isNowChanging) {
		        	double lat = jsObj.getLat();
					double lng = jsObj.getLng();
					double lat1 = lat-(0.001*slider.getValue());
					double lng1 = lng-(0.001*slider.getValue());
					double lat2 = lat+(0.001*slider.getValue());
					double lng2 = lng+(0.001*slider.getValue());
					setBounds(lat1, lng1, lat2, lng2);
					
					System.out.println("Changed: " + jsObj.getLat() + jsObj.getLat());
					webEngine.executeScript("" +
							"window.x1 = " +  (lat1)  + ";" +
							"window.y1 = " +  (lng1)  + ";" +
							"window.x2 = " +  (lat2)  + ";" +
							"window.y2 = " +  (lng2)  + ";" +
							"showBox(window.x1, window.y1, window.x2, window.y2);"
							);
		        }
		    }
		});
		
		
		//slider.setPrefSize(100, 200);
		slider.setMaxWidth(200);

		GridPane controls = new GridPane();
		controls.setPrefHeight(100);
		controls.add(slider, 2, 0);



		mapPane.setTop(controls);
		mapPane.setCenter(webView);
		webView.toFront();
		//		StackPane sPane = new StackPane();
		//		StackPane.setAlignment(g, Pos.TOP_LEFT);
		//		sPane.getChildren().addAll(webView, g);


		//slider.toFront();
		return mapPane;

	}
	
	

	
	public void setBounds(double lat1, double lng1, double lat2, double lng2)
	{
		this.bounds[0] = lat1;
		this.bounds[1] = lng1;
		this.bounds[2] = lat2;
		this.bounds[3] = lng2; 
	}

	
	public HBox getControls()
	{
		HBox buttons = new HBox();
		return buttons;
		
	}


}
