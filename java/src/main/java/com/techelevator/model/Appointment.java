package com.techelevator.model;

import java.time.LocalDateTime;

public class Appointment {

    private int id;
    private String name;
    private String status;
    private String agenda;
    private LocalDateTime date;
    private int userId;
    private int detailsId;
    private String patientFirstName;
    private String patientLastName;
    private String providerFirstName;
    private String providerLastName;

    public Appointment() {
    }

    public Appointment(int id, String name, String status, String agenda, LocalDateTime date, int userId, int detailsId,
                       String patientFirstName, String patientLastName, String providerFirstName, String providerLastName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.agenda = agenda;
        this.date = date;
        this.userId = userId;
        this.detailsId = detailsId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.providerFirstName = providerFirstName;
        this.providerLastName = providerLastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDetailsId() {
        return detailsId;
    }

    public void setProviderId(int detailsId) {
        this.detailsId = detailsId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }
}
