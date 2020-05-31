package com.nessbit.vojonbari.service;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.User;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.utils.PrefUserInfo;
import com.nessbit.vojonbari.view.activity.RecipeCategoryActivity;
import com.nessbit.vojonbari.view.activity.SignUpActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showProgressDialog;

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
        Call<List<User>> call = networkHelper.getUserInfo(mobile);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> user = response.body();
                if (dialog.isShowing()) dialog.dismiss();
                if (user.size() == 0)
                    context.startActivity(new Intent(context, SignUpActivity.class).putExtra("mobile", mobile));
                else {
                    prefUserInfo.setLoggedIn(true);
                    prefUserInfo.setUserId(user.get(0).getId());
                    prefUserInfo.setUserName(user.get(0).getUserName());
                    prefUserInfo.setUserMobile(user.get(0).getUserMobile());
                    prefUserInfo.setUserAge(user.get(0).getAge());
                    prefUserInfo.setUserStatus(user.get(0).getStatus());
                    prefUserInfo.setUserGender(user.get(0).getGender());
                    //winner
                    prefUserInfo.setWinnerMonth(0);
                    prefUserInfo.setWinnerScore("");
                    prefUserInfo.setWinnerName("No Winner Selected yet");
                    prefUserInfo.setWinnerRecipe("No Recipe Selected yet");

                    context.startActivity(new Intent(context, RecipeCategoryActivity.class));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if (dialog.isShowing()) dialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
