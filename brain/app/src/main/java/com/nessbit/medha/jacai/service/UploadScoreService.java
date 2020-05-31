package com.nessbit.medha.jacai.service;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.GetResponse;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.utils.DialogUtils;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadScoreService {
    private static final String TAG = "UploadScoreService";
    private Context context;

    public UploadScoreService(Context context) {
        this.context=context;
    }

    public void execute (int userId,int score) {
        SpotsDialog spotsDialog = DialogUtils.showSpotsDialog(context,"Uploading");
        spotsDialog.show();
        spotsDialog.setMessage("Uploading Score...");
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        Log.i(TAG, "executeID: "+userId);
        Log.i(TAG, "executeScore: "+score);

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<GetResponse> call = networkHelper.uploadScore(userId,score);
        call.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                spotsDialog.dismiss();
                if (response.body().getResponse().equalsIgnoreCase("score_uploaded")) alertDialog.setMessage("Score Uploaded");
                else alertDialog.setMessage("Score Upload Failed");
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
