package com.techelevator.controller;

import com.techelevator.dao.DetailsDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Details;
import com.techelevator.model.Office;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class DetailsController {

    DetailsDao detailsDao;
    UserDao userDao;

    public DetailsController(DetailsDao detailsDao, UserDao userDao) {
        this.detailsDao = detailsDao;
        this.userDao =userDao;
    }

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public List<Details> findAllDetails() {
        return detailsDao.findAllDetails();
    }

    @RequestMapping(path = "/details/{id}", method = RequestMethod.GET)
    public Details getDetailsById(@PathVariable int id) {
        Details details = detailsDao.getDetailsById(id);
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        } else {

            return detailsDao.getDetailsById(id);
        }
    }

    @RequestMapping(path = "/user/details/{id}", method = RequestMethod.GET)
    public Details getDetailsByUserId(@PathVariable int id) {
        Details details = detailsDao.getDetailsByUserId(id);
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        } else {

            return detailsDao.getDetailsByUserId(id);
        }
    }

    @RequestMapping(path = "/details/current", method = RequestMethod.GET)
    public Details getDetailsById(Principal principal) {
        int currentId = userDao.findIdByUsername(principal.getName());
        Details details = detailsDao.getDetailsByUserId(currentId);
        return details;
    }


    @RequestMapping(path = "provider/details/{id}", method = RequestMethod.GET)
    public Details getProviderDetailsById(@PathVariable int id) {
        Details details = detailsDao.getDetailsById(id);
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        } else {

            return detailsDao.getDetailsById(id);
        }
    }

    // TODO This path was ambiguous and needs to fixed
    @RequestMapping(path = "office/details/{id}", method = RequestMethod.GET)
    public List<Details> getDetailsByOfficeId(@PathVariable int id) {
        return detailsDao.getDetailsByOfficeId(id);
    }


    @PostMapping("/details")
    public boolean create(@Valid @RequestBody Details details, @RequestParam int userId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam boolean isProvider, @RequestParam int titleId) {
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 not found");
        }

        return detailsDao.create(userId, firstName, lastName, isProvider, titleId);
    }

    @PostMapping("/details/provider")
    public boolean createProvider(@Valid @RequestBody Details details, @RequestParam int userId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam boolean isProvider, @RequestParam int titleId, @RequestParam int officeId) {
        if (details == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "404 not found");
        }

        return detailsDao.createProvider(userId, firstName, lastName, isProvider, titleId, officeId);
    }



}


