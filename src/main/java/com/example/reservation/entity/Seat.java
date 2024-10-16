package com.example.reservation.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "SEAT_T")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private int rowNumber;
    private int seatNumber;
    private Boolean onHold = Boolean.FALSE;
    private Boolean isReserved = Boolean.FALSE;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeHold;

    @ManyToOne
    @JoinColumn(name = "seating_plan_id")
    private SeatingPlan seatingPlan;

    public Seat() {
    }

    public Seat(int rowNumber, int seatNumber, Boolean onHold, Boolean isReserved, Date timeHold, SeatingPlan seatingPlan) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.onHold = onHold;
        this.isReserved = isReserved;
        this.timeHold = timeHold;
        this.seatingPlan = seatingPlan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getOnHold() {
        return onHold;
    }

    public void setOnHold(Boolean onHold) {
        this.onHold = onHold;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public Date getTimeHold() {
        return timeHold;
    }

    public void setTimeHold(Date timeHold) {
        this.timeHold = timeHold;
    }

    public SeatingPlan getSeatingPlan() {
        return seatingPlan;
    }

    public void setSeatingPlan(SeatingPlan seatingPlan) {
        this.seatingPlan = seatingPlan;
    }
}
