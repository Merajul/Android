package com.nessbit.vojonbari.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.service.LoginService;

public class SubscribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        EditText mobileNumberEt = findViewById(R.id.mobileNumberEt);
        findViewById(R.id.submitBtn).setOnClickListener(v -> {
            String mobileNumber = mobileNumberEt.getText().toString().trim();
            if (mobileNumber.length() != 9) Toast.makeText(this, "সঠিক মোবাইল নাম্বার লিখুন", Toast.LENGTH_SHORT).show();
            else new LoginService(this).execute("+8801"+mobileNumber);
        });
    }
}
