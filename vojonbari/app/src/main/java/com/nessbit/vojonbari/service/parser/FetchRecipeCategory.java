package com.nessbit.vojonbari.service.parser;

import android.app.AlertDialog;
import android.content.Context;


import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeCategory;
import com.nessbit.vojonbari.service.network.NetworkCallback;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showFetchingDialog;


public class FetchRecipeCategory {

    private Context context;
    private NetworkCallback callback;

    public FetchRecipeCategory(Context context) {
        this.context = context;
        callback = (NetworkCallback) context;
    }

    public void execute() {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<ArrayList<RecipeCategory>> call = networkHelper.getRecipeCategory();
        call.enqueue(new Callback<ArrayList<RecipeCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeCategory>> call, Response<ArrayList<RecipeCategory>> response) {
                spotsDialog.dismiss();
                callback.onTaskFinish(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeCategory>> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
