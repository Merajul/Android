package com.nessbit.medha.jacai.service;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.User;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.utils.PrefUserInfo;
import com.nessbit.medha.jacai.view.activity.CategoryActivity;
import com.nessbit.medha.jacai.view.activity.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.medha.jacai.utils.DialogUtils.showProgressDialog;

public class LoginService {

    private Context context;
    private PrefUserInfo prefUserInfo;

    public LoginService(Context context){
        this.context=context;
        prefUserInfo = new PrefUserInfo(context);
    }

    public void execute(String mobile) {
        ProgressDialog dialog = showProgressDialog(context, "Checking Info,please wait...");
        dialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<User> call = networkHelper.getUserInfo(mobile);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (dialog.isShowing()) dialog.dismiss();
                if (user == null)
                    context.startActivity(new Intent(context, SignUpActivity.class).putExtra("mobile", mobile));
                else {
                    prefUserInfo.setLoggedIn(true);
                    prefUserInfo.setUserId(user.getId());
                    prefUserInfo.setUserName(user.getUserName());
                    prefUserInfo.setUserMobile(user.getUserMobile());
                    prefUserInfo.setUserAge(user.getAge());
                    prefUserInfo.setUserStatus(user.getStatus());
                    prefUserInfo.setUserGender(user.getGender());

                    context.startActivity(new Intent(context, CategoryActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (dialog.isShowing()) dialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
