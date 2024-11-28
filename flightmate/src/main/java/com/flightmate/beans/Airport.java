package com.flightmate.beans;

public class Airport {
    private int airportId;
    private String name;
    private String location;

    public Airport(int id, String name, String location) {
        this.airportId = id;
        this.name = name;
        this.location = location;
    }

    // Getters and Setters
    public int getAirportId() { return airportId; }
    public void setAirportId (int id) { this.airportId = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
