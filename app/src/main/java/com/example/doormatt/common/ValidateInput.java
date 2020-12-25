package com.example.doormatt.common;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {
    Context context;
    EditText email, password, confirmPassword;

    String emailInput, passwordInput, confirmPasswordInput;

    /**
     * Validates user input for LoginActivity.class
     * @param myContext
     * @param myEmail
     * @param myPassword
     */
    public ValidateInput(Context myContext, EditText myEmail, EditText myPassword) {
        context = myContext;
        email = myEmail;
        password = myPassword;
    }

    public ValidateInput(Context myContext, EditText myEmail, EditText myPassword, EditText myConfirmPassword) {
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

}
