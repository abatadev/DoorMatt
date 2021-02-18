package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.doormatt.admin.AdminMainActivity;
import com.example.doormatt.model.AdminModel;
import com.example.doormatt.common.Common;
import com.example.doormatt.model.RolesModel;
import com.example.doormatt.validation.ValidateAccountInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpAdminActivity extends AppCompatActivity {

    ValidateAccountInput validateInput;
    AdminModel adminModel;
    RolesModel rolesModel;

    EditText adminEmailEditText, adminPasswordEditText, adminConfirmPasswordEditText;
    Button adminSignUpButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference adminRef, rolesRef;

    String userId, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = "onCreate";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_admin);

        adminEmailEditText = findViewById(R.id.admin_sign_up_login_email_editText);
        adminPasswordEditText = findViewById(R.id.admin_sign_up_login_password_editText);
        adminConfirmPasswordEditText = findViewById(R.id.admin_sign_up_login_confirm_password_editText);
        adminSignUpButton = findViewById(R.id.admin_sign_up_login_submit_button);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        adminRef = mDatabase.getReference(Common.ADMIN_REF);
        rolesRef = mDatabase.getReference(Common.ROLE_REF);

        Log.d(TAG, "Admin Ref: " + adminRef.getParent());

        adminSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Admin Ref: " + adminRef.getParent());
                registerNewAdmin();
            }
        });
    }

    private void registerNewAdmin() {
        String TAG = "registerNewAdmin()";

        email = adminEmailEditText.getText().toString().trim();
        password = adminPasswordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mUser  = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = mUser.getUid().toString();

                    AdminModel adminModel = new AdminModel();

                    adminModel.setUserId(userId);
                    adminModel.setEmail(email);
                    adminModel.setPassword(password);

                    adminRef.child(userId).setValue(adminModel);

                    Log.d(TAG, "DB Ref: " + mDatabase.getReference().toString());

                    if(task.isSuccessful()) {
                        // Redirect to Admin Panel
                        Log.d(TAG, "Sign Up Success!");
                        rolesModel = new RolesModel(userId, 1);
                        rolesRef.child(userId).setValue(rolesModel);
                        Intent intent = new Intent(SignUpAdminActivity.this, AdminMainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpAdminActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpAdminActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error: " + e.getMessage());
        }

        });
    }



}