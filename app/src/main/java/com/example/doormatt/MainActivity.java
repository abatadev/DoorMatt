package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.doormatt.admin.AdminActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.GuardActivity;
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
        setContentView(R.layout.activity_new_residence);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();
        listenForUser();
    }

    private void listenForUser() {
        userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REF);

        try {
            userId = mAuth.getCurrentUser().getUid().toString();
            currentUser = mAuth.getCurrentUser().getEmail().toString();
        } catch (NullPointerException e) {
            Log.e("MainActivity.class", "Error: " + e.getMessage());
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        userRef.child(userId).child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    boolean isAdmin = snapshot.getValue(boolean.class);

                    if(isAdmin == true) {
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.child(userId).child("guard").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    boolean isGuard = snapshot.getValue(boolean.class);

                    if(isGuard == true) {
                        Intent intent   = new Intent(MainActivity.this, GuardActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}   