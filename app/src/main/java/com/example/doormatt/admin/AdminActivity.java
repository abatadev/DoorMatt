package com.example.doormatt.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doormatt.NewResidentActivity;
import com.example.doormatt.R;

public class AdminActivity extends AppCompatActivity {

    Button newResidentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.new_resident_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NewResidentActivity.class);
                startActivity(intent);
            }
        });


    }
}