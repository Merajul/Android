package com.nessbit.medha.jacai.service;

import android.app.AlertDialog;
import android.content.Context;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.GetResponse;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.utils.DialogUtils;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUser {

    private Context context;

    public UpdateUser(Context context) {
        this.context = context;
    }

    public void execute(int userId,String userName,int age) {
        SpotsDialog spotsDialog = DialogUtils.showSpotsDialog(context,"Updating");
        spotsDialog.show();
        spotsDialog.setMessage("Updating User Info...");
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<GetResponse> call = networkHelper.updateUser(userId,userName,age);
        call.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                spotsDialog.dismiss();
                if (response.body().getResponse().equalsIgnoreCase("user_updated")) alertDialog.setMessage("User Info Updated");
                else alertDialog.setMessage("User Info Update Failed");
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<GetResponse> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
