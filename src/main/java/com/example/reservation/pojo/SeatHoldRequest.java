package com.example.reservation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class SeatHoldRequest {

    private int numSeats;
    private String customerEmail;
    private int maxLevel;
    private int minLevel;

    public SeatHoldRequest(int numSeats, String customerEmail, int maxLevel, int minLevel) {
        this.numSeats = numSeats;
        this.customerEmail = customerEmail;
        this.maxLevel = maxLevel;
        this.minLevel = minLevel;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }
}
