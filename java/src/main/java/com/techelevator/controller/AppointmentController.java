package com.techelevator.controller;

import com.techelevator.dao.AppointmentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class AppointmentController {

    private AppointmentDao appointmentDao;
    private UserDao userDao;

    public AppointmentController(AppointmentDao appointmentDao, UserDao userDao) {
        this.appointmentDao = appointmentDao;
        this.userDao = userDao;
    }

    @GetMapping("/appointment/{id}")
    public Appointment getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentDao.getAppointmentByAptId(id);
        if (appointment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        } else {

            return appointmentDao.getAppointmentByAptId(id);
        }
    }


    @GetMapping("users/appointment/{id}")
    public Appointment[] findAllPatientAppointments(@PathVariable int id) {
        List<Appointment> appointment = appointmentDao.findAllAppointmentsByPatientUserId(id);
        if (appointment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        } else {

            return appointment.toArray( new Appointment[]{} );
        }
    }


    @GetMapping("details/appointment/{id}")
    public Appointment[] findAllProviderAppointments(@PathVariable int id) {
        List<Appointment> appointments = appointmentDao.findAllAppointmentsByProviderDetailsId(id);
        return appointments.toArray(new Appointment[] {});
    }

    @GetMapping("details/{id}/appointment/new")
    public Appointment[] getUnreadAppointmentsByProviderId(@PathVariable int id) {
        List<Appointment> appointments = appointmentDao.findAllNewAppointmentsByProviderDetailsId(id);
        return appointments.toArray(new Appointment[] {});
    }

    @PostMapping("appointment/{id}/read")
    public void markAppointmentAsRead(@PathVariable int id) {
        appointmentDao.setAptAsRead(id);
    }

    @PostMapping("/appointment")
    public void createAppointment(@RequestBody Appointment appointment, Principal principal) {
        appointment.setUserId(userDao.findIdByUsername(principal.getName()));
        appointmentDao.create(appointment);

    }

    @PutMapping("/appointment")
    public void updateAppointment(@RequestBody Appointment appointment) {
        appointmentDao.update(appointment);
    }

    @DeleteMapping("/appointment/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentDao.delete(id);
    }

    @GetMapping("appointment/search")
    public Appointment[] getAppointmentsByMonthAndYear(@RequestParam int month, @RequestParam int year) {
        return appointmentDao.findAllAppointmentsByMonthAndYear(month, year).toArray( new Appointment[] {});
    }
}