package com.flightmate.libs.builders;

import java.time.LocalDateTime;

import com.flightmate.beans.Airport;

public class AirportBuilder {
	private int airportId, runways;
	private String airport_name, airport_code, city, country;
	private LocalDateTime createdAt;
	
	public AirportBuilder setAirportId(int airportId) {
		this.airportId = airportId;
		return this;
	}
	
	public AirportBuilder setAirportName(String airport_name) {
		this.airport_name = airport_name;
		return this;
	}
	
	public AirportBuilder setAirportCode(String airport_code) {
		this.airport_code = airport_code;
		return this;
	}
	
	public AirportBuilder setCity(String city) {
		this.city = city;
		return this;
	}
	
	public AirportBuilder setCountry(String country) {
		this.country = country;
		return this;
	}
	
	public AirportBuilder setRunways(int runways) {
		this.runways = runways;
		return this;
	}
	
    public AirportBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

	public Airport create() {
		return new Airport(airportId, airport_name, airport_code, city, country, runways, createdAt);
	}
}
