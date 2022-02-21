package com.example.doormatt.admin.admin_ui.resident;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doormatt.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddVisitorFromResidentActivity extends AppCompatActivity {

    // Retrieve passed data intent from resident
    /*
        1. First Name
        2. Last Name
        3. Room Number
        4. Contact Number
        6. Resident ID
     */

    private TextView residentFirstNameTextView, residentLastNameTextView, residentRoomNumberTextView,
        residentContactNumberTextView;

    private EditText firstNameEditText, lastNameEditText, roomNumberEditText, contactNumberEditText, timeVisitedEditText, reasonForVisitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor_button);

        timeVisitedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh.mm.ss aa");
                String time = simpleDateFormat.format(currentTime);

                timeVisitedEditText.setText(time);
            }
        });

        residentFirstNameTextView = findViewById(R.id.CardViewResidentFirstNameTextView);
        residentLastNameTextView = findViewById(R.id.CardViewResidentLastNameTextView);
        residentRoomNumberTextView = findViewById(R.id.CardViewResidentRoomNumberTextView);
        residentContactNumberTextView = findViewById(R.id.CardViewResidentContactNumberTextView);
        firstNameEditText = findViewById(R.id.newVisitorFirstNameEditText);
        lastNameEditText = findViewById(R.id.newVisitorLastNameEditText);


        initializeFirebase();
    }

    private void initializeFirebase() {

    }


}