package com.techelevator.dao;

import com.techelevator.model.Appointment;
import com.techelevator.model.Office;

import java.util.List;

public interface OfficeDao {

    List<Office> findAll();

    Office getOfficeById(int officeId);

    Office findByAddress(String address);

    int findIdByAddress(String address);

    void create(Office office);

    void update(Office office);

    void delete(int id);

    Office getOfficeByDetailsId(int detailsId);


    List<Office> getOfficesByLocation(int locationId);

    Office getOfficeByUser(String principleUsername);

    void updateOffice(String principleUsername, Office office);
}