package com.flightmate.beans;

import java.time.LocalDateTime;

public class Flight {
    private int flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private String status;
    private LocalDateTime departureTime; 
    private LocalDateTime arrivalTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

    // Full constructor for FlightBuilder
    public Flight(int flightId, String flightNumber, String origin, String destination, String status,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, LocalDateTime createdAt) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.createdAt = createdAt;
    }

    // Default constructor for flexibility
    public Flight() {}

    // Getters and setters
    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

