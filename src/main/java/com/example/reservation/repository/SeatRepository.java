package com.example.reservation.repository;

import com.example.reservation.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(value = "select count(*) from SEAT_T t where t.IS_RESERVED = false and t.ON_HOLD = false", nativeQuery = true)
    int getAllFreeSeatCount();

    int countByOnHoldFalseAndIsReservedFalseAndSeatingPlan_levelId(int levelId);

    List<Seat> findByOnHoldFalseAndIsReservedFalseAndSeatingPlan_levelId(int levelId);

}
