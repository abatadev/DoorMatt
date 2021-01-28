package com.example.doormatt.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doormatt.R;
import com.example.doormatt.qrcode.QRCodeActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private Button newResidentButton, newGuardButton, newVisitorButton, readQRButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initViews();
    }

    private void initViews() {
        newResidentButton = findViewById(R.id.new_resident_button);
        newGuardButton = findViewById(R.id.new_guard_button);
//        newVisitorButton = findViewById(R.id.new)
        readQRButton = findViewById(R.id.read_qr_button);
        newResidentButton.setOnClickListener(this);
        newGuardButton.setOnClickListener(this);
        readQRButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.new_resident_button:
                startActivity(new Intent(AdminActivity.this, NewResidentActivity.class));
                break;
            case R.id.new_guard_button:
                startActivity(new Intent(AdminActivity.this, NewGuardActivity.class));
                break;
            case R.id.read_qr_button:
                startActivity(new Intent(AdminActivity.this, QRCodeActivity.class));
                break;
        }
    }
}