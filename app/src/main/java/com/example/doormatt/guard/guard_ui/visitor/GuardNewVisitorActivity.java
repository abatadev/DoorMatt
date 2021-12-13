package com.example.doormatt.guard.guard_ui.visitor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.ResidentModel;
import com.example.doormatt.model.VisitorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuardNewVisitorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final String TAG = GuardNewVisitorActivity.class.getSimpleName();

    private String visitorId, residentId, residentFirstName, residentLastName, residentRoomNumber, residentContactNumber, dateOfBirth;
    private TextView residentFirstNameTextView, residentLastNameTextView, residentRoomNumberTextView, residentContactNumberTextView;
    private CircleImageView residentAvatarCircleImageView;
    private ImageView dateOfBirthImageView;
    private EditText firstNameEditText, lastNameEditText, roomNumberEditText, middleNameEditText,
            contactNumberEditText, reasonForVisitEditText;
    private EditText timeVisitEditText;
    private TextView dateOfBirthTextView;
    private Button newVisitorButton;

    private DatabaseReference visitorRef, residentRef;
    private VisitorModel visitorModel = new VisitorModel();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard_visitor);
        visitorRef = FirebaseDatabase.getInstance().getReference(Common.VISITOR_REF);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);

        readResidentFromFirebase();
        initializeViews();



        dateOfBirthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        newVisitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase();
            }
        });

        timeVisitEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh.mm.ss aa");
                String time = simpleDateFormat.format(currentTime);

                timeVisitEditText.setText(time);
            }
        });

    }

    @Override
    public void onBackPressed() {
        ResidentModel residentModel = new ResidentModel();
        residentModel.setResidentStatus(Common.CHECKED_OUT);
        super.onBackPressed();
    }

    private void initializeViews() {
        residentFirstNameTextView = findViewById(R.id.CardViewResidentFirstNameTextView);
        residentLastNameTextView = findViewById(R.id.CardViewResidentLastNameTextView);
        residentRoomNumberTextView = findViewById(R.id.CardViewResidentRoomNumberTextView);
        residentContactNumberTextView = findViewById(R.id.CardViewResidentContactNumberTextView);
        residentAvatarCircleImageView = findViewById(R.id.residentCircleImageView);
        firstNameEditText = findViewById(R.id.newVisitorFirstNameEditText);
        middleNameEditText = findViewById(R.id.newMiddleNameEditText);
        lastNameEditText = findViewById(R.id.newVisitorLastNameEditText);
        dateOfBirthTextView = findViewById(R.id.newVisitorDateOfBirthTextView);
        dateOfBirthImageView = findViewById(R.id.newVisitorDateOfBirthImageView);
        roomNumberEditText = findViewById(R.id.newVisitorRoomNumberEditText);
        contactNumberEditText = findViewById(R.id.newVisitorEmergencyContactNumberEditText);
        timeVisitEditText = findViewById(R.id.newVisitorTimeVisitEditText);
        reasonForVisitEditText = findViewById(R.id.newVisitorReasonForVisitEditText);

        newVisitorButton = findViewById(R.id.newVisitorSaveButton);
    }

    private void readResidentFromFirebase() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            residentId = getIntent().getStringExtra("residentId");
            residentRef.child(residentId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    final String residentFirstName = snapshot.child("firstName").getValue().toString();
                    final String residentLastName = snapshot.child("lastName").getValue().toString();
                    final String residentRoomNumber = snapshot.child("roomNumber").getValue().toString();
                    final String residentContactNumber = snapshot.child("contactNumber").getValue().toString();
                    final String residentAvatar = snapshot.child("residentAvatar").getValue().toString();

                    residentFirstNameTextView.setText(residentFirstName);
                    residentLastNameTextView.setText(residentLastName);
                    residentRoomNumberTextView.setText(residentRoomNumber);
                    residentContactNumberTextView.setText(residentContactNumber);
                    Picasso.get().load(residentAvatar).into(residentAvatarCircleImageView);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.d(TAG, "onCancelled: " + error.getMessage());
                }
            });

        } else {
            Log.d(TAG, "readResidentFromFirebase: null");
        }
    }

    private void saveDataToFirebase() {
        Intent intent = new Intent();

        visitorId = visitorRef.push().getKey();
        residentId = intent.getStringExtra("residentId");

        residentFirstName = residentFirstNameTextView.getText().toString();
        Log.d(TAG, "saveDataToFirebase: residentFirstName" + residentFirstName);
        residentLastName = residentLastNameTextView.getText().toString();
        residentRoomNumber = residentRoomNumberTextView.getText().toString();
        residentContactNumber = residentContactNumberTextView.getText().toString();
        residentRoomNumberTextView.setText(residentRoomNumber);

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String middleName = middleNameEditText.getText().toString();
        String dateOfBirth = dateOfBirthTextView.getText().toString();
        String contactNumber = contactNumberEditText.getText().toString();
        String timeVisit = timeVisitEditText.getText().toString();
        String reasonForVisit = reasonForVisitEditText.getText().toString();

        //Resident
        visitorModel.setResidentId(residentId);
        visitorModel.setResidentFirstName(residentFirstName);
//        visitorModel.setResidentMiddleName(residentM);
        visitorModel.setResidentLastName(residentLastName);
        visitorModel.setResidentRoomNumber(residentRoomNumber);
        visitorModel.setResidentContactNumber(residentContactNumber);

        //Visitors
        visitorModel.setVisitorId(visitorId);
        visitorModel.setVisitorFirstName(firstName);
        visitorModel.setVisitorMiddleName(middleName);
        visitorModel.setVisitorLastName(lastName);
        visitorModel.setVisitorDateOfBirth(dateOfBirth);
        visitorModel.setVisitorContactNumber(contactNumber);
        visitorModel.setVisitorTimeVisited(timeVisit);
        visitorModel.setVisitorReasonForVisit(reasonForVisit);

        visitorRef
            .child(visitorId)
            .setValue(visitorModel)
            .addOnCompleteListener(task -> {
                Log.d(TAG, "onComplete: Submitted to Firebase.");
                newVisitorButton.setText("Submitted");
                newVisitorButton.setEnabled(false);
            });

    }


    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.YEAR));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateOfBirth = month + "/" + dayOfMonth + "/" + year;
        dateOfBirthTextView.setText(dateOfBirth);
    }
}
