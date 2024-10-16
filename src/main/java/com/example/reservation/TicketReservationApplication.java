package com.example.reservation;

import com.example.reservation.entity.Seat;
import com.example.reservation.entity.SeatingPlan;
import com.example.reservation.repository.SeatRepository;
import com.example.reservation.repository.SeatingPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class TicketReservationApplication {

    Logger logger = LoggerFactory.getLogger(TicketReservationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TicketReservationApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(SeatRepository seatRepository, SeatingPlanRepository seatingPlanRepository) {
        return (args) -> {
            if (seatingPlanRepository.findAll().isEmpty()) {
                var seatingPlans = List.of(
                        new SeatingPlan(1, "Orchestra", 100.00, 25, 50),
                        new SeatingPlan(2, "Main", 75.00, 20, 100),
                        new SeatingPlan(3, "Balcony 1", 50.00, 15, 100),
                        new SeatingPlan(4, "Balcony 2", 40.00, 15, 100)
                );
                var savedSeatingPlan = seatingPlanRepository.saveAll(seatingPlans);
                for (SeatingPlan sp : savedSeatingPlan) {
                    List<Seat> seats = new ArrayList<>();
                    for (int i = 0; i < sp.getNumberOfRows(); i++) {
                        for (int k = 0; k < sp.getNumberOfSeatsInEachRow(); k++) {
                            seats.add(new Seat(i, k, false, false, null, sp));
                        }
                    }
                    seatRepository.saveAll(new ArrayList<>(seats));
                }

            } else {
                logger.info("Data already filled!!");
            }
        };
    }

}
