package com.techelevator.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {

    private int id;
    private int detailsId;
    private LocalDate availableFrom;
    private LocalDate availableTo;

    public Availability(int id, int userId, LocalDate availableFrom, LocalDate availableTo) {
        this.id = id;
        this.detailsId = detailsId;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(int detailsId) {
        this.detailsId = detailsId;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDate getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
    }

    public Availability() { }
}
