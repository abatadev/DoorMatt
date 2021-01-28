package com.example.doormatt.validation;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateResidentInput {
    Context context;
    EditText firstName, lastName, roomNumber;
    String firstNameInput, lastNameInput, roomNumberInput;

    public ValidateResidentInput(Context myContext, EditText myFirstName, EditText myLastName, EditText myRoomNumber) {
        context = myContext;
        firstName = myFirstName;
        lastName = myLastName;
        roomNumber = myRoomNumber;
    }

    /**
     * Validates first name.
     * @return
     */
    public boolean validateFirstName() {
        firstNameInput = firstName.getText().toString().trim();

        if(firstNameInput.isEmpty()) {
            Toast.makeText(context, "First name is empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates last name.
     * @return
     */
    public boolean validateLastName() {
        lastNameInput = lastName.getText().toString().trim();

        if(lastNameInput.isEmpty()) {
            Toast.makeText(context, "Last name is empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates room number of USER
     * @return
     */
    public boolean validateRoomNumber() {
        roomNumberInput = roomNumber.getText().toString().trim();

        if(roomNumberInput.isEmpty()) {
            Toast.makeText(context, "Room number is empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}
