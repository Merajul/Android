package com.nessbit.vojonbari.service.parser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;


import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeDetails;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.view.activity.RecipeDetailsActivity;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showFetchingDialog;

public class FetchRecipeDetails {
    private static final String TAG = "FetchRecipeDetails";
    private Context context;

    public FetchRecipeDetails(Context context) {
        this.context = context;
    }

    public void execute(String recipeId) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<ArrayList<RecipeDetails>> call = networkHelper.getRecipeDetails(recipeId);
        call.enqueue(new Callback<ArrayList<RecipeDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeDetails>> call, Response<ArrayList<RecipeDetails>> response) {
                spotsDialog.dismiss();
                context.startActivity(new Intent(context, RecipeDetailsActivity.class).putExtra("recipeDetails",response.body()));
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeDetails>> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
