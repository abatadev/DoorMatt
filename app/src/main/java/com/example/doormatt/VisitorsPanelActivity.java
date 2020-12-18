package com.example.doormatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VisitorsPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_panel);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}