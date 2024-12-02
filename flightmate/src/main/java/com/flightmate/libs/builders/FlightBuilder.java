package com.flightmate.libs.builders;

import java.time.LocalDateTime;
import com.flightmate.beans.Flight;

public class FlightBuilder {
    private int flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private String status;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime createdAt;

    public FlightBuilder setFlightId(int flightId) {
        this.flightId = flightId;
        return this;
    }

    public FlightBuilder setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public FlightBuilder setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public FlightBuilder setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public FlightBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public FlightBuilder setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public FlightBuilder setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public FlightBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Flight create() {
        return new Flight(flightId, flightNumber, origin, destination, status, departureTime, arrivalTime, createdAt);
    }
}

