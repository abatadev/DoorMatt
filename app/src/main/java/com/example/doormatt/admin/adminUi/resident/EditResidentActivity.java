package com.example.doormatt.admin.adminUi.resident;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditResidentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final String TAG = EditResidentActivity.class.getSimpleName();
    private String residentId;
    private String dateOfBirth;

    private EditText firstNameEditText, middleNameEditText, lastNameEditText, roomNumberEditText,
        contactNumberEditText, emergencyContactPersonEditText, emergencyContactNumberEditText;
    private TextView dateOfBirthTextView;
    private Button editResidentButton;
    private ImageView dateOfBirthImageView;

    private DatabaseReference residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resident);
        initializeViews();
        retrieveResidentData();

        editResidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editResidentDetails();
            }
        });

        dateOfBirthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void initializeViews() {
        firstNameEditText = findViewById(R.id.edit_resident_first_name_editText);
        middleNameEditText = findViewById(R.id.edit_resident_middle_name_editText);
        lastNameEditText = findViewById(R.id.edit_resident_last_name_editText);
        dateOfBirthTextView = findViewById(R.id.edit_resident_date_of_birth_textView);
        roomNumberEditText = findViewById(R.id.edit_resident_room_number_textView);
        contactNumberEditText = findViewById(R.id.edit_resident_contact_number);
        emergencyContactPersonEditText = findViewById(R.id.edit_resident_emergency_contact_person);
        emergencyContactNumberEditText = findViewById(R.id.edit_resident_emergency_contact_number);
        dateOfBirthImageView = findViewById(R.id.edit_resident_date_imageView);
        editResidentButton = findViewById(R.id.edit_resident_button);
    }

    private void retrieveResidentData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            residentId = getIntent().getStringExtra("residentId");
            residentRef.child(residentId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String middleName = snapshot.child("middleName").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    String dateOfBirth = snapshot.child("dateOfBirth").getValue().toString();
                    String roomNumber = snapshot.child("roomNumber").getValue().toString();
                    String contactNumber = snapshot.child("contactNumber").getValue().toString();
                    String emergencyContactPerson = snapshot.child("emergencyContactPerson").getValue().toString();
                    String emergencyContactNumber = snapshot.child("emergencyContactNumber").getValue().toString();

                    firstNameEditText.setText(firstName);
                    middleNameEditText.setText(middleName);
                    lastNameEditText.setText(lastName);
                    dateOfBirthTextView.setText(dateOfBirth);
                    roomNumberEditText.setText(roomNumber);
                    contactNumberEditText.setText(contactNumber);
                    emergencyContactPersonEditText.setText(emergencyContactPerson);
                    emergencyContactNumberEditText.setText(emergencyContactNumber);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.d(TAG, "onCancelled: " + error.getMessage());
                }
            });
        } else {
            Log.d(TAG, "retrieveResidentData: NULL");
        }
    }

    private void editResidentDetails() {
        Map<String, Object> updateData = new HashMap<>();

        updateData.put("firstName", firstNameEditText.getText().toString());
        updateData.put("middleName", middleNameEditText.getText().toString());
        updateData.put("lastName", lastNameEditText.getText().toString());
        updateData.put("dateOfBirth", dateOfBirthTextView.getText().toString());
        updateData.put("roomNumber", roomNumberEditText.getText().toString());
        updateData.put("contactNumber", contactNumberEditText.getText().toString());
        updateData.put("emergencyContactPerson", emergencyContactPersonEditText.getText().toString());
        updateData.put("emergencyContactNumber", emergencyContactNumberEditText.getText().toString());

        updateResidentDetails(updateData);
    }

    private void updateResidentDetails(Map<String, Object> updateData) {
        residentRef.child(residentId).updateChildren(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditResidentActivity.this, "Resident entry updated.", Toast.LENGTH_SHORT).show();
                retrieveResidentData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateOfBirth = month + "/" + dayOfMonth + "/" + year;
        dateOfBirthTextView.setText(dateOfBirth);
    }

}
