package com.flightmate.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Aircraft {
    private final int aircraftId;
    private final String aircraftModel;
    private final LocalDate manufactureDate;
    private final LocalDate lastMaintenanceDate;
    private final LocalDate nextMaintenanceDate;
    private final String aircraftNotes;
    private final LocalDateTime createdAt;
    private final int administratorId; // FK reference to User
    private final int airportId;       // FK reference to Airport

    // Constructor
    public Aircraft(int aircraftId, String aircraftModel, LocalDate manufactureDate, LocalDate lastMaintenanceDate,
                    LocalDate nextMaintenanceDate, String aircraftNotes, LocalDateTime createdAt,
                    int administratorId, int airportId) {
        this.aircraftId = aircraftId;
        this.aircraftModel = aircraftModel;
        this.manufactureDate = manufactureDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.nextMaintenanceDate = nextMaintenanceDate;
        this.aircraftNotes = aircraftNotes;
        this.createdAt = createdAt;
        this.administratorId = administratorId;
        this.airportId = airportId;
    }

    // Getter methods
    public int getAircraftId() {
        return aircraftId;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public LocalDate getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public String getAircraftNotes() {
        return aircraftNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getAdministratorId() {
        return administratorId;
    }

    public int getAirportId() {
        return airportId;
    }

	public void setAircraftId(int int1) {
		// TODO Auto-generated method stub
		
	}
}

