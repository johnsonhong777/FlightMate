package com.flightmate.beans;

public class Airport {
	private int id;
	private String airport_name, city, country;
	private String airport_code;
	private int runways;	
	
	// Constructor	
	public Airport(int id, String airport_name, String airport_code, String city, String country, int runways) {
		this.id = id;
		this.airport_name = airport_name;
		this.airport_code = airport_code;
		this.city = city;
		this.country = country;
		this.runways = runways;
	}
	
	// Getter / Setter Methods
	
	public int getId() {return id;}
		
	public String getAirport_name() {
		return airport_name;
	}

	public void setAirport_name(String airport_name) {
		this.airport_name = airport_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAirport_code() {
		return airport_code;
	}

	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}

	public int getRunways() {
		return runways;
	}

	public void setRunways(int runways) {
		this.runways = runways;
	}	
	
}