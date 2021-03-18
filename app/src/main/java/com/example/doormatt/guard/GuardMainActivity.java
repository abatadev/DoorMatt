package com.example.doormatt.guard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.doormatt.R;
import com.example.doormatt.qrcode.QRScannerFragment;
import com.example.doormatt.guard.guard_ui.logs.GuardLogsFragment;

import com.example.doormatt.guard.guard_ui.resident.GuardResidentFragment;
import com.example.doormatt.guard.guard_ui.visitor.GuardVisitorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuardMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_main);
        BottomNavigationView bottomNavGuard = findViewById(R.id.guard_bottom_navigation);
        bottomNavGuard.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.guard_fragment_container,
                    new GuardLogsFragment()).commit();
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
                    selectedFragment = new QRScannerFragment();
                    break;
                case R.id.guard_nav_logout:
                    selectedFragment = new LogOutFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.guard_fragment_container,
                    selectedFragment).commit();
            return true;
    };

}
