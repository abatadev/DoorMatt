package com.example.doormatt.model;
    public class RolesModel {
        String userId;
        int accountType;

//         Firebase Role Types
//        ADMIN_ROLE = 1;
//        GUARD_ROLE = 2;
//        RESIDENT_ROLE = 3;
//        VISITOR_ROLE = 4;

        public RolesModel(String userId, int accountType) {
            this.userId = userId;
            this.accountType = accountType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }
    }

