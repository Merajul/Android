package com.nessbit.medha.jacai.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.service.LoginService;

import static com.nessbit.medha.jacai.utils.AppConstants.NO_INTERNET_TOAST;
import static com.nessbit.medha.jacai.utils.NetworkUtil.haveNetworkConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText mobileNumberEt = findViewById(R.id.mobileEt);
        findViewById(R.id.subscribeBtn).setOnClickListener(v -> {
            String mobileNumber = mobileNumberEt.getText().toString().trim();
            if (mobileNumber.length() != 9)
                Toast.makeText(this, "Number Missing", Toast.LENGTH_SHORT).show();
            else {
                if (!haveNetworkConnection(this))
                    Toast.makeText(this, NO_INTERNET_TOAST, Toast.LENGTH_SHORT).show();
                else new LoginService(this).execute("+8801" + mobileNumber);
            }

        });
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
