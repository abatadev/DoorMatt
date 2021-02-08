package com.example.doormatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.doormatt.admin.AdminMainActivity;
import com.example.doormatt.common.Common;
import com.example.doormatt.guard.GuardMainActivity;
import com.example.doormatt.resident.ResidentMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LandingPageActivity extends AppCompatActivity {
    private final String TAG = LandingPageActivity.class.getSimpleName();
    private DatabaseReference roleReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        roleReference = FirebaseDatabase.getInstance().getReference(Common.ROLE_REF);

        LoadingDialog loadingDialog = new LoadingDialog(LandingPageActivity.this);
        loadingDialog.startLoadingAnimation();


        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        Log.d(TAG,userId);

        roleReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    try {
                        int roleType = snapshot.child("accountType").getValue(int.class);
                        Log.d(TAG, "Role Type: " + roleType);

                        switch(roleType) {
                            case(Common.ADMIN_ROLE):
                                loadingDialog.dismissLoadingDialog();
                                Intent adminIntent = new Intent(LandingPageActivity.this, AdminMainActivity.class);
                                startActivity(adminIntent);
                                break;
                            case (Common.GUARD_ROLE):
                                loadingDialog.dismissLoadingDialog();
                                Intent guardIntent = new Intent(LandingPageActivity.this, GuardMainActivity.class);
                                startActivity(guardIntent);
                                break;
                            case (Common.RESIDENT_ROLE):
                                loadingDialog.dismissLoadingDialog();
                                Intent residentIntent = new Intent(LandingPageActivity.this, ResidentMainActivity.class);
                                startActivity(residentIntent);
                                break;
                            default:
                                loadingDialog.dismissLoadingDialog();
                                Log.d(TAG, "Triggered default.");
                                break;
                        }
                    } catch(NullPointerException e) {
                        Log.d(TAG, "NPE Error: " + e.getMessage());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });
    }
}