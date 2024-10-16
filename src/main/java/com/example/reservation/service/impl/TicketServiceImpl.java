package com.example.reservation.service.impl;

import com.example.reservation.entity.Seat;
import com.example.reservation.exception.InvalidDataException;
import com.example.reservation.exception.NotEnoughSeatException;
import com.example.reservation.exception.SeatingLevelNotFound;
import com.example.reservation.entity.SeatHold;
import com.example.reservation.repository.SeatHoldRepository;
import com.example.reservation.repository.SeatRepository;
import com.example.reservation.repository.SeatingPlanRepository;
import com.example.reservation.service.HoldSchedulerService;
import com.example.reservation.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatingPlanRepository seatingPlanRepository;

    @Autowired
    private SeatHoldRepository seatHoldRepository;

    @Autowired
    private HoldSchedulerService holdSchedulerService;

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        if (venueLevel.isPresent()) {
            seatingPlanRepository.findByLevelId(venueLevel.get()).orElseThrow(() -> new SeatingLevelNotFound("Not able to find level."));
            return seatRepository.countByOnHoldFalseAndIsReservedFalseAndSeatingPlan_levelId(venueLevel.get());
        }
        return seatRepository.getAllFreeSeatCount();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {

        if (numSeats < 1 || numSeats > numSeatsAvailable(Optional.empty())) {
            throw new NotEnoughSeatException("Not Enough seats.");
        }
        int start = seatingPlanRepository.findMinLevel();
        int end = seatingPlanRepository.findMaxLevel();
        if (maxLevel.isPresent() && maxLevel.get() > 0) {
            end = maxLevel.get();
        }
        if (minLevel.isPresent() && minLevel.get() > 0) {
            start = minLevel.get();
        }
        logger.info("Start= {} and End={}", start, end);
        List<Seat> seatToHold = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<Seat> availableSeatsByLevel = seatRepository.findByOnHoldFalseAndIsReservedFalseAndSeatingPlan_levelId(i);
            if (!availableSeatsByLevel.isEmpty()) {
                int count = Math.min(numSeats, availableSeatsByLevel.size());
                seatToHold.addAll(availableSeatsByLevel.subList(0, count));
                numSeats = numSeats - count;
            }
            if (numSeats == 0) break;
        }
        logger.info("numSeats={}", numSeats);
        if (!seatToHold.isEmpty() && numSeats == 0) {
            seatToHold.forEach(s ->
            {
                s.setOnHold(true);
                s.setTimeHold(new Date());
            });
            seatRepository.saveAll(seatToHold);
            SeatHold seatHold = new SeatHold();
            seatHold.setCustomerEmail(customerEmail);
            seatHold.setSeats(seatToHold);
            seatHoldRepository.save(seatHold);
            holdSchedulerService.releaseHold(seatHold.getId());
            logger.info("Hold at HoldId=" + seatHold.getId());
            return seatHold;
        }
        throw new NotEnoughSeatException("Not Enough seat at the level");
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHold = seatHoldRepository.findById((long) seatHoldId).
                orElseThrow(() -> new InvalidDataException("Time lapsed, please re-hold the tickets."));
        if (seatHold.getReserved()) {
            throw new InvalidDataException("Booking with holdId " + seatHoldId + " already confirmed.");
        }
        if (customerEmail.equalsIgnoreCase(seatHold.getCustomerEmail())) {
            var reservingSeats = seatHold.getSeats().stream().peek(s -> {
                s.setOnHold(false);
                s.setReserved(true);
            }).toList();
            seatRepository.saveAll(reservingSeats);
            seatHold.setReserved(true);
            seatHoldRepository.save(seatHold);
            return "RESERVED_ID_SH_" + seatHoldId;
        }
        throw new InvalidDataException("Email not matched!!");
    }
}
