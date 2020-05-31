package com.nessbit.medha.jacai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.utils.PrefUserInfo;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PrefUserInfo prefUserInfo = new PrefUserInfo(this);

        new Handler().postDelayed(() -> {
            if (prefUserInfo.getLoggedIn()) startActivity(new Intent(this, CategoryActivity.class));
            else startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_TIME_OUT);
    }
}
