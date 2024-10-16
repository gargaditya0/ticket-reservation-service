package com.example.reservation.pojo;

public class SeatReserveRequest {

    private int seatHoldId;
    String customerEmail;


    public SeatReserveRequest(int seatHoldId, String customerEmail) {
        this.seatHoldId = seatHoldId;
        this.customerEmail = customerEmail;
    }

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
