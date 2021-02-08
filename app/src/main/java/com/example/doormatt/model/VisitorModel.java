package com.example.doormatt.model;

public class VisitorModel {
    String visitorId, visitorName, dateOfBirth, residentId, residentFirstName, residentLastName, residentRoomNumber , residentQRCode;

    public VisitorModel() {
    }

    public VisitorModel(String visitorId, String visitorName, String dateOfBirth, String residentId, String residentFirstName, String residentLastName, String residentRoomNumber, String residentQRCode) {
        this.visitorId = visitorId;
        this.visitorName = visitorName;
        this.dateOfBirth = dateOfBirth;
        this.residentId = residentId;
        this.residentFirstName = residentFirstName;
        this.residentLastName = residentLastName;
        this.residentRoomNumber = residentRoomNumber;
        this.residentQRCode = residentQRCode;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getResidentFirstName() {
        return residentFirstName;
    }

    public void setResidentFirstName(String residentFirstName) {
        this.residentFirstName = residentFirstName;
    }

    public String getResidentLastName() {
        return residentLastName;
    }

    public void setResidentLastName(String residentLastName) {
        this.residentLastName = residentLastName;
    }

    public String getResidentRoomNumber() {
        return residentRoomNumber;
    }

    public void setResidentRoomNumber(String residentRoomNumber) {
        this.residentRoomNumber = residentRoomNumber;
    }

    public String getResidentQRCode() {
        return residentQRCode;
    }

    public void setResidentQRCode(String residentQRCode) {
        this.residentQRCode = residentQRCode;
    }
}
