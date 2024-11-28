package com.flightmate.beans;

public class Aircraft {
    private int id;
    private String model;
    private String details;
    private int airportId; // Foreign key referencing Airport

    public Aircraft(int id, String model, String details, int airportId) {
        this.id = id;
        this.model = model;
        this.details = details;
        this.airportId = airportId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public int getAirportId() { return airportId; }
    public void setAirportId(int airportId) { this.airportId = airportId; }
}