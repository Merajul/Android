package com.nessbit.vojonbari.service;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.GetResponse;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.utils.PrefUserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewService {
    private static final String TAG = "ReviewService";
    private Context context;
    private PrefUserInfo prefUserInfo;

    public ReviewService(Context context) {
        this.context = context;
        prefUserInfo = new PrefUserInfo(context);
    }

    public void execute(String recipeId, String rating, String reviewText) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<GetResponse> call = networkHelper.review(recipeId, prefUserInfo.getUserId(), prefUserInfo.getUserName(), rating, reviewText);
        call.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                Log.i(TAG, "onResponse: "+response.body().getResponse());
                if (response.body().getResponse().equalsIgnoreCase("review_posted")) {
                    alertDialog.setMessage("Review Succesfully Posted");
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("Review Post Failed");
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<GetResponse> call, Throwable t) {
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
