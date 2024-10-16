package com.example.reservation.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SEAT_HOLD_T")
public class SeatHold {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = false)
    private List<Seat> seats = new ArrayList<>();

    private String customerEmail;

    private Boolean isReserved = Boolean.FALSE;

    public SeatHold() {
    }

    public SeatHold(List<Seat> seats, String customerEmail) {
        this.seats = seats;
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
