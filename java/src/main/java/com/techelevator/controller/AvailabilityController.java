package com.techelevator.controller;

import com.techelevator.dao.AvailabilityDao;
import com.techelevator.dao.DetailsDao;
import com.techelevator.model.Availability;
import com.techelevator.model.Details;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class AvailabilityController {

    AvailabilityDao availabilityDao;

    public AvailabilityController(AvailabilityDao availabilityDao) {
        this.availabilityDao = availabilityDao;
    }

    @RequestMapping(path = "/availability/{id}", method = RequestMethod.GET)
    public Availability getAvailabilityById(@PathVariable int id) {
        Availability details = availabilityDao.getAvailabilityById(id);
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This User's availability has not been specified");
        } else {

            return availabilityDao.getAvailabilityById(id);
        }
    }

    @RequestMapping(path = "details/availability/{id}", method = RequestMethod.GET)
    public Availability getAvailabilityByDetailsId(@PathVariable int id) {
        Availability details = availabilityDao.getAvailabilityByDetailsId(id);
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This User's availability has not been specified");
        } else {

            return availabilityDao.getAvailabilityByDetailsId(id);
        }
    }
}
