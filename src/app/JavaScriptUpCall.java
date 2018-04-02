package app;

public class JavaScriptUpCall {

	protected double latLng[];
	
	public JavaScriptUpCall() {
		this.latLng = new double[2];
	}
	public void changeLatLong(String name)	
	{
		
		System.out.println(name);

		try {
			String[] latlng = name.replaceAll("[()]", "").split(",");
			this.latLng[0] = Double.parseDouble(latlng[0]);
			this.latLng[1] = Double.parseDouble(latlng[1]);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.out.println(this.latLng[0] + " " + this.latLng[1]);
		
	}
	
	public double getLat()
	{
		return this.latLng[0];
	}
	
	public double getLng()
	{
		return this.latLng[1];
	}
	
	
	
}
