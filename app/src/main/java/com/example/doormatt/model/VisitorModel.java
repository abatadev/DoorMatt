package com.example.doormatt.model;

public class VisitorModel {
    String visitorId, visitorName, timeVisited, reasonForVisit;
    String residentId, residentFirstName, residentLastName, residentRoomNumber, residentQRCode;

    public VisitorModel() {
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

    public String getTimeVisited() {
        return timeVisited;
    }

    public void setTimeVisited(String timeVisited) {
        this.timeVisited = timeVisited;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
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
