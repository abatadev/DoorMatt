package com.example.doormatt.model;
    public class RolesModel {
        String userId;
        String accountType;

        public RolesModel(String userId, String accountType) {
            this.userId = userId;
            this.accountType = accountType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }
    }

