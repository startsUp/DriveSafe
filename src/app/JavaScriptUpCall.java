package app;

import java.util.ArrayList;
import java.util.List;

public class JavaScriptUpCall {

	protected List<double[]> latLng;
	
	public JavaScriptUpCall() {
		this.latLng = new ArrayList<>();
	}
	public void changeLatLong(String ltlng)	
	{
		
		System.out.println(ltlng);
		
		try {
			String[] latlng = ltlng.replaceAll("[()]", "").split(",");
			this.latLng.add(new double[] {Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1])});
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}
	
	public double getLat(int i)
	{
		return this.latLng.get(i)[0];
	}
	public double getLastLat()
	{
		return this.latLng.get(latLng.size()-1)[0];
	}
	public double getLastLng()
	{
		return this.latLng.get(latLng.size()-1)[1];
	}
	public double getLng(int i)
	{
		return this.latLng.get(i)[1];
	}
	public int length()
	{
		return this.latLng.size();
	}
	public void emptyList()
	{
		this.latLng.clear();
	}
	
	
	
}
