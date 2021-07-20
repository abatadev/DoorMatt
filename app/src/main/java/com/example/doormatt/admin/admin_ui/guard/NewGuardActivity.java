package com.example.doormatt.admin.admin_ui.guard;

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
import com.example.doormatt.model.RolesModel;
import com.example.doormatt.validation.ValidateGuardInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private DatabaseReference guardReference, roleReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    String email, name, password, confirmPassword;

    GuardModel guardModel = new GuardModel();
    RolesModel rolesModel;
    ValidateGuardInput validateGuardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard);
        mAuth = FirebaseAuth.getInstance();
        guardReference = FirebaseDatabase.getInstance().getReference(Common.GUARD_REF);
        roleReference = FirebaseDatabase.getInstance().getReference(Common.ROLE_REF);

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
                registerNewGuard();
            }
        });
    }

    private void registerNewGuard() {
        Log.d(TAG, "registerNewGuard: ");
        email = guardEmailEditText.getText().toString().trim();
        name = guardNameEditText.getText().toString().trim();
        password = guardPasswordEditText.getText().toString().trim();
        confirmPassword = guardConfirmPasswordEditText.getText().toString().trim();
        Log.d(TAG, String.format("Email: %s\nName: %s\nPassword: %s\nConfirm Password: %s", email, name, password, confirmPassword));

        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete: " + task.isSuccessful());
                if(task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    String guardId = user.getUid();

                    GuardModel guardModel = new GuardModel();
                    guardModel.setGuardUserId(guardId);
                    guardModel.setGuardEmail(email);
                    guardModel.setGuardFullName(name);
                    guardModel.setGuardPassword(password);
                    guardReference.child(guardId).setValue(guardModel).addOnSuccessListener(guardTask -> {
                        Toast.makeText(NewGuardActivity.this, "Done.", Toast.LENGTH_SHORT).show();

                        RolesModel rolesModel = new RolesModel(guardId, 2);
                        roleReference.child(guardId).setValue(rolesModel).addOnSuccessListener(roleTask -> Log.d(TAG, "onSuccess: Role added to Database."));
                    });

                }

        }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "onFailure: " + e.getMessage());
            e.printStackTrace();
        });


    }
/*
    private void submitToFirebase() {
        String guardName, guardEmail, guardPassword;

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
//                            guardModel.isAdmin(false);
//                            guardModel.isGuard(true);

                            rolesModel = new RolesModel(guardId, 2);
                            if (task.isSuccessful()) {
                                // Redirect to Admin Panel
                                guardReference.child(guardId).setValue(guardModel)
                                        .addOnFailureListener(e -> Log.d(TAG, "Error: " + e.getMessage()));
                                Log.d(TAG, "Added Guard Success!");
                                Toast.makeText(NewGuardActivity.this, "Registered to Database.", Toast.LENGTH_SHORT).show();
                                saveGuardButton.setText("Submitted");
                                Log.d(TAG, "Registered guard to database");
                                saveGuardButton.setEnabled(false);
                                Toast.makeText(NewGuardActivity.this, "Submitted to Database.", Toast.LENGTH_SHORT).show();

                                roleReference.child(guardId).setValue(rolesModel);
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
    }*/
}