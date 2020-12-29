package com.example.doormatt.model;

public class ResidentModel {
    String residentId, firstName, lastName, dateOfBirth, roomNumber, residentAvatar;

    public ResidentModel() {
    }

    public ResidentModel(String residentId, String firstName, String lastName, String dateOfBirth, String roomNumber, String residentAvatar) {
        this.residentId = residentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.roomNumber = roomNumber;
        this.residentAvatar = residentAvatar;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getResidentAvatar() {
        return residentAvatar;
    }

    public void setResidentAvatar(String residentAvatar) {
        this.residentAvatar = residentAvatar;
    }
}
