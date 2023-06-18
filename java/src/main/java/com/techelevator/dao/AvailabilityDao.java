package com.techelevator.dao;

import com.techelevator.model.Availability;
import com.techelevator.model.Details;

import java.time.LocalDate;

public interface AvailabilityDao {

    Availability getAvailabilityByDetailsId(int detailsId);

    Availability getAvailabilityById(int id);

    boolean create(int detailsId, LocalDate availableFrom, LocalDate availableTo);
}
