package com.example.doormatt.Common;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ValidateInput {
    Context context;
    TextInputEditText username, password;

    String usernameInput, passwordInput;

    /**
     * Validates user input for LoginActivity.class
     * @param myContext
     * @param myUsername
     * @param myPassword
     */
    public ValidateInput(Context myContext, TextInputEditText myUsername, TextInputEditText myPassword) {
        context = myContext;
        username = myUsername;
        password = myPassword;
    }

    public boolean validateUsername() {
        usernameInput = username.getText().toString().trim();

        if(usernameInput.isEmpty()) {
            Toast.makeText(context, "Your username is empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword() {
        passwordInput = password.getText().toString().trim();

        if(passwordInput.isEmpty()) {
            Toast.makeText(context, "Your password is empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
