package com.example.doormatt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doormatt.R;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.GuardModel;
import com.example.doormatt.validation.ValidateGuardInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class NewGuardActivity extends AppCompatActivity {
    private final String TAG = NewGuardActivity.class.getSimpleName();

    private EditText guardEmailEditText, guardNameEditText, guardPasswordEditText, guardConfirmPasswordEditText;
    private Button saveGuardButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference guardReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    GuardModel guardModel = new GuardModel();
    ValidateGuardInput validateGuardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard);

        guardEmailEditText = findViewById(R.id.guardEmailEditText);
        guardNameEditText = findViewById(R.id.guardNameEditText);
        guardPasswordEditText = findViewById(R.id.guardPasswordEditText);
        guardConfirmPasswordEditText = findViewById(R.id.guardConfirmPasswordEditText);
        saveGuardButton = findViewById(R.id.saveGuardButton);

        validateGuardInput = new ValidateGuardInput(
                NewGuardActivity.this, guardEmailEditText, guardNameEditText,
                guardPasswordEditText, guardConfirmPasswordEditText);

        saveGuardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                registerToUsers();
                submitToFirebase();
            }
        });
    }

    private void submitToFirebase() {
        String guardName, guardEmail, guardPassword;
        guardReference = FirebaseDatabase.getInstance().getReference(Common.GUARD_REF);
        mAuth = FirebaseAuth.getInstance();

        boolean guardNameVerified = validateGuardInput.validateGuardName();
        boolean guardEmailVerified = validateGuardInput.validateGuardEmail();
        boolean guardPasswordVerified = validateGuardInput.validateGuardPassword();
        boolean guardConfirmPasswordVerified = validateGuardInput.validateGuardConfirmPassword();

        guardName = guardNameEditText.getText().toString();
        guardEmail = guardEmailEditText.getText().toString();
        guardPassword = guardPasswordEditText.getText().toString();

        if (guardNameVerified && guardEmailVerified && guardPasswordVerified && guardConfirmPasswordVerified) {
            final String guardId = guardReference.push().getKey();
            Log.d(TAG, "Guard ID: " + guardId);

            try {
                mAuth.createUserWithEmailAndPassword(guardEmail, guardPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            guardModel.setGuardUserId(guardId);
                            guardModel.setGuardFullName(guardName);
                            guardModel.setGuardEmail(guardEmail);
                            guardModel.setGuardPassword(guardPassword);
                            guardModel.isAdmin(false);
                            guardModel.isGuard(true);
//                                Log.d(TAG, "DB Ref: " + guardReference.toString());

                            if (task.isSuccessful()) {
                                // Redirect to Admin Panel
                                guardReference.child(guardId).setValue(guardModel)
                                        .addOnFailureListener(e -> Log.d(TAG, "Error: " + e.getMessage()));
                                Log.d(TAG, "Added Guard Success!");
                                saveGuardButton.setText("Submitted");
                                Log.d(TAG, "Registered guard to database");
                                saveGuardButton.setEnabled(false);
                                Toast.makeText(NewGuardActivity.this, "Submitted to Database.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Error: " + e.getMessage());
                        }
                    });
            } catch (NullPointerException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }
}