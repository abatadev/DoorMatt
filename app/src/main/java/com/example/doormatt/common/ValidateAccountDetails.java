package com.example.doormatt.common;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doormatt.NewResidentActivity;

public class ValidateAccountDetails {
    Context context;
    EditText email, password, confirmPassword;
    String emailInput, passwordInput, confirmPasswordInput;

    /**
     * Validates user input for LoginActivity.class
     * @param myContext
     * @param myEmail
     * @param myPassword
     */

    public ValidateAccountDetails(Context myContext, EditText myEmail, EditText myPassword) {
        context = myContext;
        email = myEmail;
        password = myPassword;
    }


    public ValidateAccountDetails(Context myContext, EditText myEmail, EditText myPassword, EditText myConfirmPassword) {
        context = myContext;
        email = myEmail;
        password = myPassword;
        confirmPassword = myConfirmPassword;

    }

    public boolean validateUsername() {
        emailInput = email.getText().toString().trim();

        if(emailInput.isEmpty()) {
            Toast.makeText(context, "E-mail Address cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword() {
        passwordInput = password.getText().toString().trim();

        if(passwordInput.isEmpty()) {
            Toast.makeText(context, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateConfirmPassword() {
        confirmPasswordInput = confirmPassword.toString().trim();

        if(confirmPasswordInput.isEmpty()) {
            Toast.makeText(context, "Confirm password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
//
//    public boolean validateFirstName() {
//        firstNameInput = firstName.getText().toString().trim();
//
//        if(firstNameInput.isEmpty()) {
//            Toast.makeText(context, "First name cannot be empty.", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean validateLastName() {
//        lastNameInput = lastName.getText().toString().trim();
//
//        if(firstNameInput.isEmpty()) {
//            Toast.makeText(context, "Last name cannot be empty.", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean validateRoomNumber() {
//        roomNumberInput = roomNumber.getText().toString().trim();
//
//        if(roomNumberInput.isEmpty()) {
//            Toast.makeText(context, "Room number cannot be empty.", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }

}
