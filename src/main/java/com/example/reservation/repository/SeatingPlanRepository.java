package com.example.reservation.repository;

import com.example.reservation.entity.SeatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, Long> {
    Optional<SeatingPlan> findByLevelId(int levelId);

    @Query("SELECT MAX(s.levelId) FROM SeatingPlan s")
    int findMaxLevel();

    @Query("SELECT MIN(s.levelId) FROM SeatingPlan s")
    int findMinLevel();
}
