package com.example.reservation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SEATING_PLAN_T")
public class SeatingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int levelId;
    private String levelName;
    private double price;
    private int numberOfRows;
    private int numberOfSeatsInEachRow;

    public SeatingPlan() {
    }

    public SeatingPlan(int levelId, String levelName, double price, int numberOfRows, int numberOfSeatsInEachRow) {
        this.levelId = levelId;
        this.levelName = levelName;
        this.price = price;
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsInEachRow = numberOfSeatsInEachRow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfSeatsInEachRow() {
        return numberOfSeatsInEachRow;
    }

    public void setNumberOfSeatsInEachRow(int numberOfSeatsInEachRow) {
        this.numberOfSeatsInEachRow = numberOfSeatsInEachRow;
    }
}
