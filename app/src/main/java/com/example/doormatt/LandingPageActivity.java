package com.example.doormatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        LoadingDialog loadingDialog = new LoadingDialog(LandingPageActivity.this);
//        loadingDialog.startLoadingAnimation();
//        loadingDialog.dismissLoadingDialog();
    }
}