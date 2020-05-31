package com.nessbit.medha.jacai.service.parser;

import android.app.AlertDialog;
import android.content.Context;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Winner;
import com.nessbit.medha.jacai.service.network.NetworkCallback;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.utils.DialogUtils;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchWinner {

    private static final String TAG = "FetchWinner";
    private Context context;
    private NetworkCallback callback;

    public FetchWinner(Context context) {
        this.context=context;
        callback=(NetworkCallback)context;
    }

    public void execute() {
        SpotsDialog spotsDialog = DialogUtils.showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<Winner> call = networkHelper.getWinner();
        call.enqueue(new Callback<Winner>() {
            @Override
            public void onResponse(Call<Winner> call, Response<Winner> response) {
                spotsDialog.dismiss();
                callback.onTaskFinish(response.body(),TAG);
            }

            @Override
            public void onFailure(Call<Winner> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
