package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.doormatt.admin.AdminActivity;
import com.example.doormatt.admin.AdminMainActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.GuardMainActivity;
import com.example.doormatt.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference userRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    String userId, currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        listenForUser();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void listenForUser() {
        userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REF);

        try {
            userId = mAuth.getCurrentUser().getUid().toString();
            currentUser = mAuth.getCurrentUser().getEmail().toString();
            Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
            intent.putExtra("userId", mAuth.getUid());
            startActivity(intent);
        } catch (NullPointerException e) {
            Log.e("MainActivity.class", "Error: " + e.getMessage());
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }


}   