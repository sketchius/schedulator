package com.techelevator.model;

public class Patient {

    private int id;
    private String firstName;
    private String lastName;
    private int titleId;
    private int userId;

    public Patient() { }
    public Patient(int id, String firstName,
                   String lastName, int titleId, int userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.titleId = titleId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
