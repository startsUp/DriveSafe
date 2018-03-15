package dataHandler;

import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;

public class BoundBox {
	private ArrayList<Violation> data;
	private ArrayList<Violation> refined_data_X;
	private ArrayList<Violation> refined_data_XY ;
	private ArrayList<Violation> refined_data;
	
	public BoundBox(ArrayList<Violation> data){
		this.data = data;
	}
	public void Bounding(ArrayList<Violation> a, double x_lo, double x_hi , double y_lo, double y_hi){
		int first_index = 0;
		int last_index = 75;
		//first_index = data.indexOf(x_lo);
		//last_index = data.indexOf(x_hi);
		if(first_index == -1 || last_index == -1){
			System.out.println("Could not find points in range");
			return;
		}
		else{
			refined_data_X = new ArrayList<>();
			for (int i = first_index; i <= last_index; i++){
				refined_data_X.add(this.data.get(i));
			}
		}
		sorting s = new sorting(refined_data_X);
		s.sort(refined_data_X, 1);
		refined_data_XY = s.getdata();
		/*
		for(int i = 0; i < refined_data_XY.size()-1; i++){
			if (!sorting.isSorted(refined_data_XY.get(i).getLatlong()[1],refined_data_XY.get(i+1).getLatlong()[1])){
				System.out.println("Ooops bamboozle BOUNDBOX");
			}
		}
		System.out.println("No bamboozle BOUNDBOX");
		*/
		//first_index = refined_data_XY.indexOf(y_lo);
		//last_index = refined_data_XY.indexOf(y_hi);
		first_index = 0;
		last_index = 20;
		if(first_index == -1 || last_index == -1){
			System.out.println("Could not find points in range");
			return;
		}
		else{
			refined_data = new ArrayList<>();
			for (int i = first_index; i <= last_index; i++){
				refined_data.add(refined_data_XY.get(i));
			}	
		}
		System.out.println("++++++++++++++++++++++++++++++++++++");
		for(int i = 0; i < refined_data.size(); i++){
			
			System.out.printf("%s:%s\n",a.get(i).getLatlong()[0] , a.get(i).getLatlong()[1]);
		}
		System.out.println("++++++++++++++++++++++++++++++++++++");
		
		
	}
	
	

}
