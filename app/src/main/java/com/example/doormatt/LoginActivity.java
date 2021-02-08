package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doormatt.validation.ValidateAccountInput;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    TextView signUpAdminTextView;

    Button submitButton;

    String username, password;

    ValidateAccountInput validateAccountDetails;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        usernameEditText = findViewById(R.id.login_username_editText);
        passwordEditText = findViewById(R.id.login_password_editText);
        submitButton = findViewById(R.id.login_button);

        signUpAdminTextView = findViewById(R.id.login_sign_up_admin_textView);

        //validateInput
        validateAccountDetails = new ValidateAccountInput(
                LoginActivity.this,
                usernameEditText,
                passwordEditText
        );

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {

        } else {

        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });

        signUpAdminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void signInUser() {
        boolean usernameVerified = validateAccountDetails.validateUsername();
        boolean passwordVerified = validateAccountDetails.validatePassword();

        if(usernameVerified && passwordVerified) {
            username = usernameEditText.getText().toString().trim();
            password = passwordEditText.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                        intent.putExtra("userId", mAuth.getUid());
                        startActivity(intent);
                    }
                })
            .addOnFailureListener(e -> {
                Log.e("signInUser", "Error: " + e.getMessage());
                Toast.makeText(this, "[E]" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show();
        }
    }

}