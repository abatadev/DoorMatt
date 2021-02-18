package com.example.doormatt.guard.guardUi.resident;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.guardUi.visitor.GuardNewVisitorActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class GuardResidentDetailedActivity extends AppCompatActivity {
    private final String TAG = GuardResidentDetailedActivity.class.getSimpleName();

    Button newVisitorButton;
    private DatabaseReference residentRef;
    private String myResidentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_resident_detailed);
        residentRef = FirebaseDatabase.getInstance().getReference(Common.RESIDENT_REF);
        newVisitorButton = findViewById(R.id.addNewVisitorGuardButton);

        String residentId = getIntent().getStringExtra("residentId");

        Log.d(TAG, "onCreate: residentId: " + residentId);
        residentRef.child(residentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    myResidentId = snapshot.child("residentId").getValue(String.class);
                    Log.d(TAG, "onDataChange: myResidentId : " + myResidentId);
                    String myFirstName = snapshot.child("firstName").getValue().toString();

                    newVisitorButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GuardResidentDetailedActivity.this, GuardNewVisitorActivity.class);
                            intent.putExtra(myResidentId, "residentId");
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }
}