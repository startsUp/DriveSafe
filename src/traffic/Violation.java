package traffic;


public class Violation {

	/*

	 List of columns in the data-set:
	
	 [Date Of Stop, Time Of Stop, Agency, SubAgency, 
	 Description, Location, Latitude, Longitude, Accident,
	 Belts, Personal Injury, Property Damage, Fatal, Commercial License,
	 HAZMAT, Commercial Vehicle, Alcohol, Work Zone, State, VehicleType,
	 Year, Make, Model, Color, Violation Type, Charge, Article, 
	 Contributed To Accident, Race, Gender, Driver City, Driver State, 
	 DL State, Arrest Type, Geolocation]
	 
	 
	 
	 */
	
	
	private String timeOfStop;
	private String agency;
	private double[] latlong;
	private String description;
	//private Driver driver;
	//private Vehicle vehicle;
	private boolean fatal;
	private String violationType;
	private String dlState;
	private String dateOfViolation;
	
	
	
	public Violation(String dateOfViolation, String timeOfStop, String agency, double[] latlong, String description) {
		
		this.dateOfViolation = dateOfViolation;
		this.timeOfStop = timeOfStop;
		this.agency = agency;
		this.latlong = latlong;
		this.description = description;
//		this.fatal = fatal;
//		this.violationType = violationType;
//		this.dlState = dlState;
		
	}

	
	public String getTimeOfStop() {
		return timeOfStop;
	}

	public String getAgency() {
		return agency;
	}

	public double[] getLatlong() {
		return latlong;
	}

	public String getDescription() {
		return description;
	}

	public boolean isFatal() {
		return fatal;
	}

	public String getViolationType() {
		return violationType;
	}

	public String getDlState() {
		return dlState;
	}

	public String getDateOfViolation() {
		return dateOfViolation;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescription();
	}
	
	
}
