package com.example.doormatt.guard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doormatt.LoginActivity;
import com.example.doormatt.R;
import com.example.doormatt.admin.ui.guard.AdminGuardFragment;
import com.example.doormatt.admin.ui.logs.AdminLogsFragment;
import com.example.doormatt.admin.ui.qr.AdminQRFragment;
import com.example.doormatt.admin.ui.resident.AdminResidentFragment;
import com.example.doormatt.guard.ui.logs.GuardLogsFragment;
import com.example.doormatt.guard.ui.qr.GuardQRFragment;
import com.example.doormatt.guard.ui.resident.GuardResidentFragment;
import com.example.doormatt.guard.ui.visitor.GuardVisitorFragment;
import com.example.doormatt.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class GuardMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_main);
        BottomNavigationView bottomNavGuard = findViewById(R.id.guard_bottom_navigation);
        bottomNavGuard.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.guard_fragment_container,
                    new GuardResidentFragment()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.guard_nav_logs:
                    selectedFragment = new GuardLogsFragment();
                    break;
                case R.id.guard_nav_resident:
                    selectedFragment = new GuardResidentFragment();
                    break;
                case R.id.guard_nav_visitor:
                    selectedFragment = new GuardVisitorFragment();
                    break;
                case R.id.guard_nav_qr_fragment:
                    selectedFragment = new GuardQRFragment();
                    break;
                case R.id.guard_nav_logout:
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.guard_fragment_container,
                    selectedFragment).commit();
            return true;
    };

    private void signOut() {
        UserModel userModel = null;

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }

}
