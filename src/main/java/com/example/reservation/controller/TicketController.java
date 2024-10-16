package com.example.reservation.controller;

import com.example.reservation.entity.SeatHold;
import com.example.reservation.pojo.SeatHoldRequest;
import com.example.reservation.pojo.SeatReserveRequest;
import com.example.reservation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/available")
    public ResponseEntity<Integer> getAllAvailableSeatCount(@RequestParam(name = "levelId", required = false) String levelId) {
        Optional<Integer> level = !levelId.isEmpty() ? Optional.of(Integer.valueOf(levelId)) : Optional.empty();
        return ResponseEntity.ok(ticketService.numSeatsAvailable(level));
    }

    @PostMapping("/hold")
    public ResponseEntity<SeatHold> findAndHoldSeat(@RequestBody SeatHoldRequest seatHoldRequest) {
        return
                ResponseEntity.ok(ticketService.findAndHoldSeats(seatHoldRequest.getNumSeats(),
                        Optional.ofNullable(seatHoldRequest.getMinLevel()), Optional.ofNullable(seatHoldRequest.getMaxLevel())
                        , seatHoldRequest.getCustomerEmail()));
    }

    @PostMapping("/reserve")
    ResponseEntity<String> reserveSeat(@RequestBody SeatReserveRequest seatReserveRequest) {
        return ResponseEntity.ok(ticketService.reserveSeats(seatReserveRequest.getSeatHoldId(), seatReserveRequest.getCustomerEmail()));
    }


}
