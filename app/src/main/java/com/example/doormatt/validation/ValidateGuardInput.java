package com.example.doormatt.validation;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doormatt.common.Common;

public class ValidateGuardInput {
    Context context;

    EditText guardEmailEditText, guardNameEditText, guardPasswordEditText,
            guardConfirmPasswordEditText;

    String guardEmailInput, guardNameInput, guardPasswordInput, guardConfirmPasswordInput;

    public ValidateGuardInput(Context myContext, EditText myGuardEmail, EditText myGuardName,
                              EditText myGuardPassword, EditText myGuardConfirmPassword) {
        context = myContext;
        guardEmailEditText = myGuardEmail;
        guardNameEditText = myGuardName;
        guardPasswordEditText = myGuardPassword;
        guardConfirmPasswordEditText = myGuardConfirmPassword;

    }

    public boolean validateGuardName() {
        guardNameInput = guardNameEditText.getText().toString();

        if(guardNameInput.isEmpty()) {
            Toast.makeText(context, "Guard's name cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(guardNameInput.toString().trim().matches(Common.NAME_PATTERN)) {
                //Name is valid
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean validateGuardEmail() {
        guardEmailInput = guardEmailEditText.getText().toString();
        
        if(guardEmailInput.isEmpty()) {
            Toast.makeText(context, "Guard's E-mail cannot be empty.", Toast.LENGTH_SHORT).show();
            return  false;
        } else {
            if(Patterns.EMAIL_ADDRESS.matcher(guardEmailInput).matches()) {
                //Email is valid
                return true;
            } else {
                Toast.makeText(context, "Invalid E-mail address.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        
    }
    
    public boolean validateGuardPassword() {
        guardPasswordInput = guardPasswordEditText.getText().toString();
        
        if(guardPasswordInput.isEmpty()) {
            Toast.makeText(context, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    
    public boolean validateGuardConfirmPassword() {
        guardConfirmPasswordInput = guardConfirmPasswordEditText.getText().toString();
        
        if(guardConfirmPasswordInput.isEmpty()) {
            Toast.makeText(context, "Confirm password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
