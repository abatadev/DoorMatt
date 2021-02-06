package com.example.doormatt.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.guard.AdminGuardFragment;
import com.example.doormatt.admin.ui.logs.AdminLogsFragment;
import com.example.doormatt.admin.ui.qr.AdminQRFragment;
import com.example.doormatt.admin.ui.resident.AdminResidentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        BottomNavigationView bottonNavAdmin = findViewById(R.id.admin_bottom_navigation);
        bottonNavAdmin.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                    new AdminResidentFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.admin_nav_resident:
                    selectedFragment = new AdminResidentFragment();
                    break;
                case R.id.admin_nav_guard:
                    selectedFragment = new AdminGuardFragment();
                    break;
                case R.id.admin_nav_logs:
                    selectedFragment = new AdminLogsFragment();
                    break;
                case R.id.admin_nav_qr_scanner:
                    selectedFragment = new AdminQRFragment();
                    break;
                case R.id.admin_nav_logout:
//                            selectedFragment = new SearchFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };
}