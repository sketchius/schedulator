package com.techelevator.dao;

import com.techelevator.model.Appointment;

import java.util.List;

public interface AppointmentDao {

    List<Appointment> findAllAppointments();

    Appointment getAppointmentByAptId(int aptId);

    List<Appointment> findAllAppointmentsByPatientUserId(int userId);

    List<Appointment> findAllAppointmentsByMonthAndYear(int month, int year);

    List<Appointment> findAllAppointmentsByProviderDetailsId(int providerId);

    List<Appointment> findAllAppointmentsBothIds(int userId,int providerId);

    List<Appointment> findAllNewAppointmentsByProviderDetailsId(int providerId);

    void setAptAsRead(int aptId);

    int findIdByAptName(String aptName);

    void create(Appointment appointment);

    void update(Appointment appointment);

    void delete(int id);
}
