package dataHandler;

import java.util.ArrayList;

import sorting.SortList;
import traffic.Violation;

public class BoundBox {
	
	
	
	private static ArrayList<Violation> refined_data_X;
	//private static ArrayList<Violation> refined_data_XY ;
	private static ArrayList<Violation> refined_data;
	
	public BoundBox(){
		
	}
	
	
	public ArrayList<Violation> Bounding(ArrayList<Violation> a, double x_lo, double x_hi , double y_lo, double y_hi){
		
		ArrayList<Violation> temp = new ArrayList<>();
		/*
		for (int i=0; i < 200; i++){
			temp.add(a.get(i));
			System.out.println(a.get(i));
		}
		*/
		//SortList.sort(a, 0);
		
		int first_index = 0;
		int last_index = 75;
		first_index = firstIndex(a,x_lo,0);
		
		last_index = lastIndex(a,x_hi,0);
		
		
		
		
		if(first_index == -1 || last_index == -1){
			System.out.println("Could not find points in range");
			return a;
		}
		else{
			refined_data_X = new ArrayList<Violation>();
			for (int i = first_index; i <= last_index; i++){
				refined_data_X.add(a.get(i));
			}
		}
		
		SortList.sort(refined_data_X, 1);
		
		/*
		for(int i = 0; i < refined_data_XY.size()-1; i++){
			if (!sorting.isSorted(refined_data_XY.get(i).getLatlong()[1],refined_data_XY.get(i+1).getLatlong()[1])){
				System.out.println("Ooops bamboozle BOUNDBOX");
			}
		}
		System.out.println("No bamboozle BOUNDBOX");
		*/
		
		first_index = firstIndex(refined_data_X, y_lo, 1);
		
		last_index = lastIndex(refined_data_X, y_hi, 1);
	
		//first_index = 0;
		//last_index = 20;
		if(first_index == 0 || last_index == 0){
			
			return a;
		}
		else{
			refined_data = new ArrayList<Violation>();
			for (int i = first_index; i <= last_index; i++){
				refined_data.add(refined_data_X.get(i));
			}	
		}
		/*
		System.out.println("++++++++++++++++++++++++++++++++++++");
		for(int i = 0; i < refined_data.size(); i++){
			
			System.out.printf("%s:%s\n",refined_data.get(i).getLatlong()[0] , refined_data.get(i).getLatlong()[1]);
		}
		System.out.println("++++++++++++++++++++++++++++++++++++");
		*/
		return refined_data;
	}

	private static int firstIndex(ArrayList<Violation> arraylist,double lo, int flag) {
		for (int i = 0; i < arraylist.size() - 1; i++){
            if (Double.parseDouble(arraylist.get(i).getLatlong()[flag]) <= lo &&  lo <= Double.parseDouble(arraylist.get(i+1).getLatlong()[flag]))
                return i+1;
		}
		return 0;
	}
	
	
	
	
	private static int lastIndex(ArrayList<Violation> arraylist,double hi, int flag) {
		for (int i = arraylist.size() - 1; i >= 0; i--){
			//System.out.printf("%s < %f and %f < %s\n",arraylist.get(i-1).getLatlong()[flag],hi,hi,arraylist.get(i).getLatlong()[flag]);
			if (Double.parseDouble(arraylist.get(i-1).getLatlong()[flag]) <= hi &&  hi <= Double.parseDouble(arraylist.get(i).getLatlong()[flag]))
                return i-1;
		}
		return 0;
	}



	
	
	/*
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
    */

	
	
	

}
