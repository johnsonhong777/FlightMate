package com.flightmate.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class FlightHour {
    private int id;
    private int pilotId;
    private Date flightDate;
    private BigDecimal hoursFlighted;
    private String notes;
    private String approvalStatus;
    private Timestamp createdAt;

   
    public FlightHour(int id, int pilotId, Date flightDate, BigDecimal hoursFlighted, String notes, String approvalStatus, Timestamp createdAt) {
        this.id = id;
        this.pilotId = pilotId;
        this.flightDate = flightDate;
        this.hoursFlighted = hoursFlighted;
        this.notes = notes;
        this.approvalStatus = approvalStatus;
        this.createdAt = createdAt;
    }

 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPilotId() {
        return pilotId;
    }

    public void setPilotId(int pilotId) {
        this.pilotId = pilotId;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public BigDecimal getHoursFlighted() {
        return hoursFlighted;
    }

    public void setHoursFlighted(BigDecimal hoursFlighted) {
        this.hoursFlighted = hoursFlighted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
