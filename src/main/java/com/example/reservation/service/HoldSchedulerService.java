package com.example.reservation.service;

import com.example.reservation.entity.SeatHold;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public interface HoldSchedulerService {

    void releaseHold(Long id);

}
