package com.example.reservation.repository;

import com.example.reservation.entity.SeatHold;
import com.example.reservation.entity.SeatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatHoldRepository extends JpaRepository<SeatHold, Long> {
    List<SeatHold> findByIsReservedFalse();
}
