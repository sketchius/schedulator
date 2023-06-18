package com.techelevator.controller;

import com.techelevator.dao.OfficeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Office;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class OfficeController {

    private OfficeDao officeDao;

    public OfficeController(OfficeDao officeDao) {
        this.officeDao = officeDao;
    }

    @GetMapping("/office")
    public List<Office> listAllOffices() {
        return officeDao.findAll();
    }

    @GetMapping("/office/{id}")
    public Office getOfficeById(@PathVariable int id) {

        Office office = officeDao.getOfficeById(id);
        if (office == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found");
        } else {
        return officeDao.getOfficeById(id);
        }
    }

    @GetMapping("/details/office/{id}")
    public Office getOfficeByDetailsId(@PathVariable int id) {

        Office office = officeDao.getOfficeByDetailsId(id);
        if (office == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found");
        } else {
            return officeDao.getOfficeByDetailsId(id);
        }
    }

    @GetMapping("/myOffices")
    public Office getOfficeByUser(Principal principal) {
        return officeDao.getOfficeByUser(principal.getName());
    }

    @GetMapping("/office/location/{locationId}")
    public List<Office> getOfficesByLocation(@PathVariable int locationId) {
        List<Office> offices = officeDao.getOfficesByLocation(locationId);
        if (offices == null || offices.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No offices found for this location");
        }
        return offices;
    }
    @PostMapping("/office")
    public void createOffice(@RequestBody Office office) {
        officeDao.create(office);

    }

    @PutMapping("/office")
    public void updateOffice(@RequestBody Office office) {
        officeDao.update(office);

    }
    @PutMapping("/updateOffice")
    public void updateOfficeToProvider(Principal principal, @RequestBody Office office) {
        officeDao.updateOffice(principal.getName(), office);
    }

    @DeleteMapping("/office/{id}")
    public void deleteOffice(@PathVariable int id) {
        officeDao.delete(id);

    }
}
