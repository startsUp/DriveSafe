package app;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import dataHandler.BoundBox;
import dataHandler.ParseJSON;
import graphing.Edge;
import graphing.Graph;
import graphing.Vertex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

		long st = System.currentTimeMillis();
		
		HashMap<String, Integer> trafficData = hashData(data);
		ParseJSON parse = new ParseJSON();
		Vertex[] graph = parse.getVertices();
		//Graph.addViolationWeight(graph,  trafficData);
		


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
		
		
		
		Button plotGraphData = new Button("plot");
		controls.add(plotGraphData, 1, 0);
		plotGraphData.setOnAction(e->{
			BoundBox b = new BoundBox();
			ArrayList<Violation> t = b.Bounding(data, bounds[0], bounds[2], bounds[1], bounds[3]);
			webEngine.executeScript("clearDataPoints();");
			for(Violation v: t) {
				webEngine.executeScript("" +
						"window.lat = " + v.getLatlong()[0] /*loc.lat*/ + ";" +
						"window.lon = " + v.getLatlong()[1]/*loc.lon*/ + ";"+
						"addDataPoint(window.lat, window.lon);"
						);
			}
			webEngine.executeScript("showHeatMap();");
			ParseJSON p = new ParseJSON();
			int i =0;
			for(Vertex v: graph)
			{
				double lat = v.getLa();
				double lng = v.getLo();

				if(i>10000) break;
				webEngine.executeScript("" +
						"window.lat = " + lat /*loc.lat*/ + ";" +
						"window.lon = " + lng /*loc.lon*/ + ";"+
						"document.addMarker(window.lat, window.lon);"
						);

				for(Edge edge: v.getE()) {

					double lat1 = graph[edge.getI()].getLa();
					double lng1 = graph[edge.getI()].getLo();

					System.out.println(lat + " " + lng + " " + lat1 + " " + lng1 + " " + edge.getW());
					try {
						webEngine.executeScript("" +
								"window.lat  = " + lat /*loc.lat*/ + ";" +
								"window.lon = " +  lng/*loc.lon*/ + ";"+
								"window.lat1 = " + lat1/*loc.lon*/ + ";"+
								"window.lat2 = " + lng1/*loc.lon*/ + ";"+
								"drawLine(window.lat, window.lon, window.lat1, window.lon1);"
								);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
				i++;
			}

		});




		mapPane.setTop(controls);
		mapPane.setCenter(webView);
		webView.toFront();
		//		StackPane sPane = new StackPane();
		//		StackPane.setAlignment(g, Pos.TOP_LEFT);
		//		sPane.getChildren().addAll(webView, g);


		//slider.toFront();
		return mapPane;

	}




	private HashMap<String, Integer> hashData(ArrayList<Violation> data) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> latlngViolations = new HashMap<>();
		for(Violation v: data)
		{ 
			String[] latLng = v.getLatlong();
			String key= "";
			//key = lat+long, accurate upto 3 decimal points
			key = latLng[0].trim().substring(0, Math.min(latLng[0].length(), 6)) + latLng[1].trim().substring(0, Math.min(latLng[1].length()-1, 7));
			//System.out.println(key);
			
			if(latlngViolations.containsKey(key))
				latlngViolations.put(key, latlngViolations.get(key)+1);
			else
				latlngViolations.put(key, 1);
		}
		return null;
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
