package com.example.doormatt.model;

public class VisitorModel {
    String visitorId, visitorFirstName, visitorMiddleName, visitorLastName, visitorDateOfBirth, visitorContactNumber, visitorTimeVisited, visitorReasonForVisit;
    String residentId, residentFirstName, residentMiddleName, residentLastName, residentRoomNumber, residentContactNumber, residentQRCode;

    public VisitorModel() {
    }

    public VisitorModel(String visitorId, String visitorFirstName, String visitorMiddleName,
                        String visitorLastName, String visitorDateOfBirth, String visitorContactNumber,
                        String visitorTimeVisited, String visitorReasonForVisit, String residentId,
                        String residentFirstName, String residentMiddleName, String residentLastName,
                        String residentRoomNumber, String residentContactNumber, String residentQRCode) {

        this.visitorId = visitorId;
        this.visitorFirstName = visitorFirstName;
        this.visitorMiddleName = visitorMiddleName;
        this.visitorLastName = visitorLastName;
        this.visitorDateOfBirth = visitorDateOfBirth;
        this.visitorContactNumber = visitorContactNumber;
        this.visitorTimeVisited = visitorTimeVisited;
        this.visitorReasonForVisit = visitorReasonForVisit;
        this.residentId = residentId;
        this.residentFirstName = residentFirstName;
        this.residentMiddleName = residentMiddleName;
        this.residentLastName = residentLastName;
        this.residentRoomNumber = residentRoomNumber;
        this.residentContactNumber = residentContactNumber;
        this.residentQRCode = residentQRCode;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorFirstName() {
        return visitorFirstName;
    }

    public void setVisitorFirstName(String visitorFirstName) {
        this.visitorFirstName = visitorFirstName;
    }

    public String getVisitorMiddleName() {
        return visitorMiddleName;
    }

    public void setVisitorMiddleName(String visitorMiddleName) {
        this.visitorMiddleName = visitorMiddleName;
    }

    public String getVisitorLastName() {
        return visitorLastName;
    }

    public void setVisitorLastName(String visitorLastName) {
        this.visitorLastName = visitorLastName;
    }

    public String getVisitorDateOfBirth() {
        return visitorDateOfBirth;
    }

    public void setVisitorDateOfBirth(String visitorDateOfBirth) {
        this.visitorDateOfBirth = visitorDateOfBirth;
    }

    public String getVisitorContactNumber() {
        return visitorContactNumber;
    }

    public void setVisitorContactNumber(String visitorContactNumber) {
        this.visitorContactNumber = visitorContactNumber;
    }

    public String getVisitorTimeVisited() {
        return visitorTimeVisited;
    }

    public void setVisitorTimeVisited(String visitorTimeVisited) {
        this.visitorTimeVisited = visitorTimeVisited;
    }

    public String getVisitorReasonForVisit() {
        return visitorReasonForVisit;
    }

    public void setVisitorReasonForVisit(String visitorReasonForVisit) {
        this.visitorReasonForVisit = visitorReasonForVisit;
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

    public String getResidentMiddleName() {
        return residentMiddleName;
    }

    public void setResidentMiddleName(String residentMiddleName) {
        this.residentMiddleName = residentMiddleName;
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

    public String getResidentContactNumber() {
        return residentContactNumber;
    }

    public void setResidentContactNumber(String residentContactNumber) {
        this.residentContactNumber = residentContactNumber;
    }

    public String getResidentQRCode() {
        return residentQRCode;
    }

    public void setResidentQRCode(String residentQRCode) {
        this.residentQRCode = residentQRCode;
    }
}