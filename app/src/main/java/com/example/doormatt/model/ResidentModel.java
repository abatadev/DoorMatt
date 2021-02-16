package com.example.doormatt.model;

public class ResidentModel {
    String residentId, firstName, lastName, dateOfBirth, roomNumber, contactNumber,
            emergencyContactNumber, emergencyContactPerson, residentAvatar, qrCode;

    int residentStatus;

    public ResidentModel() {
    }

    public ResidentModel(int residentStatus) {
        this.residentStatus = residentStatus;
    }

    public ResidentModel(String residentId, String firstName, String lastName, String dateOfBirth,
                         String roomNumber, String contactNumber, String emergencyContactNumber,
                         String emergencyContactPerson, String residentAvatar, String qrCode,
                         int residentStatus) {

        this.residentId = residentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.roomNumber = roomNumber;
        this.contactNumber = contactNumber;
        this.emergencyContactNumber = emergencyContactNumber;
        this.emergencyContactPerson = emergencyContactPerson;
        this.residentAvatar = residentAvatar;
        this.qrCode = qrCode;
        this.residentStatus = residentStatus;
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

    public int getResidentStatus() {
        return residentStatus;
    }

    public void setResidentStatus(int residentStatus) {
        this.residentStatus = residentStatus;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public String getResidentAvatar() {
        return residentAvatar;
    }

    public void setResidentAvatar(String residentAvatar) {
        this.residentAvatar = residentAvatar;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
