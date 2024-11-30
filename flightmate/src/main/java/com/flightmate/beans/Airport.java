package com.flightmate.beans;

import java.time.LocalDateTime;

public class Airport {
    private int airportId;
	private String airportName, city, country;
	private String airportCode;
	private int runways;	
	private LocalDateTime createdAt;
	
	// Constructor	
	public Airport(int airportId, String airportName, String airportCode, String city, String country, int runways, LocalDateTime createdAt) {
		this.airportId = airportId;
		this.airportName = airportName;
		this.airportCode = airportCode;
		this.city = city;
		this.country = country;
		this.runways = runways;
		this.createdAt = createdAt;
	}
	
	// Getter / Setter Methods
	
	public int getAirportId() {return airportId;}
		
	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public int getRunways() {
		return runways;
	}

	public void setRunways(int runways) {
		this.runways = runways;
	}	

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
