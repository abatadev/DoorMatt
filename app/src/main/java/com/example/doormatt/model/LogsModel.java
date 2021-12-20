package com.example.doormatt.model;

import java.util.Map;

public class LogsModel {
    public String logId, residentId, guardId, residentFirstname, residentMiddleName, residentLastName, residentContactNumber, residentRoomNumber, guardName;
    public int residentStatus;
    public String dateRecorded, timeRecorded;

    public LogsModel() {
    }

    public LogsModel(String logId, String residentId, String guardId, String residentFirstname,
                     String residentMiddleName, String residentLastName, String residentContactNumber,
                     String residentRoomNumber, String guardName, int residentStatus,
                     String dateRecorded, String timeRecorded) {

        this.logId = logId;
        this.residentId = residentId;
        this.guardId = guardId;
        this.residentFirstname = residentFirstname;
        this.residentMiddleName = residentMiddleName;
        this.residentLastName = residentLastName;
        this.residentContactNumber = residentContactNumber;
        this.residentRoomNumber = residentRoomNumber;
        this.guardName = guardName;
        this.residentStatus = residentStatus;
        this.dateRecorded = dateRecorded;
        this.timeRecorded = timeRecorded;

    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getGuardId() {
        return guardId;
    }

    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    public String getResidentFirstname() {
        return residentFirstname;
    }

    public void setResidentFirstname(String residentFirstname) {
        this.residentFirstname = residentFirstname;
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

    public String getResidentContactNumber() {
        return residentContactNumber;
    }

    public void setResidentContactNumber(String residentContactNumber) {
        this.residentContactNumber = residentContactNumber;
    }

    public String getResidentRoomNumber() {
        return residentRoomNumber;
    }

    public void setResidentRoomNumber(String residentRoomNumber) {
        this.residentRoomNumber = residentRoomNumber;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public int getResidentStatus() {
        return residentStatus;
    }

    public void setResidentStatus(int residentStatus) {
        this.residentStatus = residentStatus;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(String timeRecorded) {
        this.timeRecorded = timeRecorded;
    }
}
