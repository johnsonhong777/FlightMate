package com.flightmate.libs.builders;

import com.flightmate.beans.Aircraft;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AircraftBuilder {
    private int aircraftId;
    private String aircraftModel;
    private LocalDate manufactureDate;
    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDate;
    private String aircraftNotes;
    private LocalDateTime createdAt;
    private int administratorId; // FK reference to User
    private int airportId;       // FK reference to Airport

    // Setter methods for builder pattern
    public AircraftBuilder setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
        return this;
    }

    public AircraftBuilder setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public AircraftBuilder setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public AircraftBuilder setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
        return this;
    }

    public AircraftBuilder setNextMaintenanceDate(LocalDate nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
        return this;
    }

    public AircraftBuilder setAircraftNotes(String aircraftNotes) {
        this.aircraftNotes = aircraftNotes;
        return this;
    }

    public AircraftBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AircraftBuilder setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
        return this;
    }

    public AircraftBuilder setAirportId(int airportId) {
        this.airportId = airportId;
        return this;
    }

    // Method to create the Aircraft object
    public Aircraft create() {
        return new Aircraft(aircraftId, aircraftModel, manufactureDate, lastMaintenanceDate,
                nextMaintenanceDate, aircraftNotes, createdAt, administratorId, airportId);
    }
}
