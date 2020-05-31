package com.nessbit.medha.jacai.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.service.SignUpService;
import com.nessbit.medha.jacai.utils.SpinnerUtil;

import java.util.ArrayList;

import static com.nessbit.medha.jacai.utils.AppConstants.FILL_FORM;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText userNameEt = findViewById(R.id.nameEt);
        EditText ageEt = findViewById(R.id.ageEt);
        Spinner genderSn = findViewById(R.id.genderSn);

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Others");
        SpinnerUtil.showSpinner(this,genderSn,genderList);

        findViewById(R.id.signUpBtn).setOnClickListener(v -> {
            String userName = userNameEt.getText().toString().trim();
            String age = ageEt.getText().toString().trim();
            String gender = genderSn.getSelectedItem().toString();
            String mobile = getIntent().getStringExtra("mobile").trim();
            String device = Build.BRAND+" "+Build.MODEL;

            if (mobile.isEmpty()) Toast.makeText(this, "SESSION FAILED", Toast.LENGTH_SHORT).show();
            else if (userName.equalsIgnoreCase("admin"))
                Toast.makeText(this, userName+" not Allowed", Toast.LENGTH_SHORT).show();
            else if (!(userName.isEmpty() || age.isEmpty() || gender.isEmpty()))
                new SignUpService(this).execute(userName,mobile,age,gender,device);
            else Toast.makeText(this, FILL_FORM, Toast.LENGTH_SHORT).show();
        });
    }
}
