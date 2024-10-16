package com.example.reservation.service.impl;

import com.example.reservation.entity.SeatHold;
import com.example.reservation.repository.SeatHoldRepository;
import com.example.reservation.repository.SeatRepository;
import com.example.reservation.service.HoldSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class HoldSchedulerServiceImpl implements HoldSchedulerService {

    Logger logger = LoggerFactory.getLogger(HoldSchedulerServiceImpl.class);

    @Value(("${hold.timeout}"))
    private int timeLimit;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatHoldRepository seatHoldRepository;


    ScheduledExecutorService holdScheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void releaseHold(Long id) {
        System.out.println("Timelimit=" + timeLimit);
        holdScheduler.schedule(() -> {
            SeatHold seatHold = seatHoldRepository.findById(id).orElseThrow();
            if (!seatHold.getReserved()) {
                var seats = seatHold.getSeats().stream().peek(s -> s.setOnHold(false)).toList();
                seatRepository.saveAll(seats);
                seatHoldRepository.delete(seatHold);
                logger.info("Released hold for {}", seatHold.getId());
            }
        }, timeLimit, TimeUnit.SECONDS);
    }

    @Scheduled(fixedRate = 60000)
    private void releaseHoldScheduler() {
        logger.info("Running scheduler...");
        var unReservedSeatHolds = seatHoldRepository.findByIsReservedFalse();
        unReservedSeatHolds.forEach(seatHold -> {
            if (!seatHold.getReserved()) {
                Date currentTime = new Date();
                Date expiryTime = new Date(seatHold.getSeats().get(0).getTimeHold().getTime() + 6000);
                if (currentTime.after(expiryTime)) {
                    var seats = seatHold.getSeats().stream().peek(s -> s.setOnHold(false)).toList();
                    seatRepository.saveAll(seats);
                    seatHoldRepository.delete(seatHold);
                    logger.info("Released hold for {} in scheduler.", seatHold.getId());
                }
            }
        });

    }

}
