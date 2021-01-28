package com.example.doormatt.model;

public class GuardModel {
    String guardUserId, guardEmail, guardFullName, guardPassword;
    boolean isGuard, isAdmin;

    public GuardModel() {
    }

    public GuardModel(String guardUserId, String guardEmail, String guardFullName, String guardPassword, boolean isGuard, boolean isAdmin) {
        this.guardUserId = guardUserId;
        this.guardEmail = guardEmail;
        this.guardFullName = guardFullName;
        this.guardPassword = guardPassword;
        this.isGuard = isGuard;
        this.isAdmin = isAdmin;
    }

    public String getGuardUserId() {
        return guardUserId;
    }

    public void setGuardUserId(String guardUserId) {
        this.guardUserId = guardUserId;
    }

    public String getGuardEmail() {
        return guardEmail;
    }

    public void setGuardEmail(String guardEmail) {
        this.guardEmail = guardEmail;
    }

    public String getGuardFullName() {
        return guardFullName;
    }

    public void setGuardFullName(String guardFullName) {
        this.guardFullName = guardFullName;
    }

    public String getGuardPassword() {
        return guardPassword;
    }

    public void setGuardPassword(String guardPassword) {
        this.guardPassword = guardPassword;
    }

    public boolean isGuard(boolean isGuard) {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }

    public boolean isAdmin(boolean isAdmin) {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
