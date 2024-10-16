package com.example.reservation.service.impl;

import com.example.reservation.entity.Seat;
import com.example.reservation.entity.SeatingPlan;
import com.example.reservation.exception.InvalidDataException;
import com.example.reservation.exception.NotEnoughSeatException;
import com.example.reservation.repository.SeatHoldRepository;
import com.example.reservation.repository.SeatRepository;
import com.example.reservation.repository.SeatingPlanRepository;
import com.example.reservation.service.HoldSchedulerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
class TicketServiceImplTest {

    @Autowired
    private TicketServiceImpl ticketService;

    @Autowired
    private SeatHoldRepository seatHoldRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatingPlanRepository seatingPlanRepository;

    @Autowired
    private HoldSchedulerService holdSchedulerService;

    @Mock
    private ScheduledExecutorService holdScheduler;

    static List<SeatingPlan> seatingPlans = new ArrayList<>();
    static List<Seat> seats = new ArrayList<>();

    @Test
    void numSeatsAvailable() {
        int count = ticketService.numSeatsAvailable(Optional.empty());
        assertEquals(6250, count);
    }

    @Test
    void numSeatsAvailableWithLevelId() {
        int count = ticketService.numSeatsAvailable(Optional.of(1));
        assertEquals(1250, count);
    }

    @Test
    void findAndHoldSeatsNonSeatAvailable() {
        RuntimeException thrown = assertThrows(NotEnoughSeatException.class, () ->
                ticketService.findAndHoldSeats(7099, Optional.empty(), Optional.empty(), "abc@mail.com"));
        assertEquals("Not Enough seats.", thrown.getMessage());
    }

    @Test
    void findAndHoldSeatsNonSeatAvailableAtLevel() {
        RuntimeException thrown = assertThrows(NotEnoughSeatException.class, () ->
                ticketService.findAndHoldSeats(5000, Optional.of(4), Optional.empty(), "abc@mail.com"));
        assertEquals("Not Enough seat at the level", thrown.getMessage());
    }

    @Test
    void findAndHoldSeatsAtLevel1() {
        var seatHold = ticketService.findAndHoldSeats(100, Optional.of(1), Optional.empty(), "abc@mail.com");
        assertEquals(true, seatHold.getSeats().get(0).getOnHold());
        assertEquals(100, seatHold.getSeats().size());
    }

    @Test
    void reserveSeats() throws InterruptedException {
        var seatHold = ticketService.findAndHoldSeats(100, Optional.of(1), Optional.empty(), "abc@mail.com");

        int id = Math.toIntExact(seatHold.getId());

        var res = ticketService.reserveSeats(id, "abc@mail.com");

        assertEquals("RESERVED_ID_SH_" + id, res);
    }

    @Test
    void reserveSeatsWrongId() throws InterruptedException {
        RuntimeException thrown = assertThrows(InvalidDataException.class, () ->
                ticketService.reserveSeats(100, "abc@mail.com"));

        assertEquals("Time lapsed, please re-hold the tickets.", thrown.getMessage());
    }


}