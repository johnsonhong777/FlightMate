package com.flightmate.libs.builders;

import com.flightmate.beans.Airport;
import java.time.LocalDateTime;

public class AirportBuilder {
    private int airportId;
    private String airportCode;
    private String airportName;
    private String city;
    private String country;
    private LocalDateTime createdAt;

    public AirportBuilder setAirportId(int airportId) {
        this.airportId = airportId;
        return this;
    }

    public AirportBuilder setAirportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public AirportBuilder setAirportName(String airportName) {
        this.airportName = airportName;
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

    public AirportBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Airport create() {
        return new Airport(airportId, airportCode, airportName, city, country, createdAt);
    }
}
