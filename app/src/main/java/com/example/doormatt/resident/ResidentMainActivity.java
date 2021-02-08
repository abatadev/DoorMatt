package com.example.doormatt.resident;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.doormatt.R;
import com.example.doormatt.admin.ui.qr.AdminQRFragment;
import com.example.doormatt.admin.ui.resident.AdminResidentFragment;
import com.example.doormatt.resident.ui.profile.ResidentProfileFragment;
import com.example.doormatt.resident.ui.logs.ResidentLogsFragment;
import com.example.doormatt.resident.ui.status.ResidentStatusFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResidentMainActivity extends AppCompatActivity {

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
                        case R.id.resident_nav_profile:
                            selectedFragment = new ResidentProfileFragment();
                            break;
                        case R.id.resident_nav_status:
                            selectedFragment = new ResidentStatusFragment();
                            break;
                        case R.id.resident_nav_logs:
                            selectedFragment = new ResidentLogsFragment();
                            break;
                        case R.id.resident_nav_logout:
                            selectedFragment = new AdminQRFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
