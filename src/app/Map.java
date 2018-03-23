package app;

import java.net.URL;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import traffic.*;
import netscape.javascript.JSObject;

public class Map {

	public BorderPane getGooogleMap(ArrayList<Violation> data){

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
		JSObject win = 
				(JSObject) webEngine.executeScript("window");
		//win.setMember(, value);

		//plot all the pins
//		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
//			@Override
//			public void changed(ObservableValue<? extends State> value,
//					State oldState, State newState) {
//				if(newState == State.SUCCEEDED){
//				
//						for(Violation v: data){
//
//							String[] latlong = v.getLatlong();
//							webEngine.executeScript("" +
//									"window.lat = " + latlong[0] + ";" +
//									"window.lon = " +  latlong[1] + ";"+
//									"var locName =  \""+ v.getState() + "\"" +";"+   //Location Name 
//									"document.addMarker(window.lat, window.lon, locName);"
//									);
//						}
//					}
//				}
//			
//		});


		mapPane.setTop(prog);
		mapPane.setCenter(webView);







		//		reload.setOnAction(e-> webEngine.reload());
		//		rightB.getChildren().addAll(reload, add);






		//		//ADD PINS TO MAP FOR ADDED LOCS
		//		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
		//			@Override
		//			public void changed(ObservableValue<? extends State> value,
		//					State oldState, State newState) {
		//				if(newState == State.SUCCEEDED){
		//					ArrayList<String> extraLocs = allLocations.getAddedLoc();
		//
		//					if(extraLocs.size()!=0){
		//						for(String nameLoc: extraLocs){
		//
		//							Location l = locations.get(locIndex.get(nameLoc));
		//							webEngine.executeScript("" +
		//									"window.lat = " + l.getLat() /*loc.lat*/ + ";" +
		//									"window.lon = " + l.getLon() /*loc.lon*/ + ";"+
		//									"var locName =  \""+ nameLoc+ "\"" +";"+   //Location Name 
		//									"document.addMarker(window.lat, window.lon, locName);"
		//									);
		//						}
		//					}
		//				}
		//			}
		//		});






		return mapPane;

	}
}
