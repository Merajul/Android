package com.nessbit.vojonbari.service.parser;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.Winner;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.view.fragment.RecipeWinnerFragment;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showFetchingDialog;

public class FetchWinner {

    private Context context;

    public FetchWinner(Context context) {
        this.context = context;
    }

    public void execute(int winnerMonth, int desiredMonth) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<ArrayList<Winner>> call = networkHelper.getWinner(winnerMonth, desiredMonth);
        call.enqueue(new Callback<ArrayList<Winner>>() {
            @Override
            public void onResponse(Call<ArrayList<Winner>> call, Response<ArrayList<Winner>> response) {
                spotsDialog.dismiss();

                Bundle args = new Bundle();
                args.putParcelableArrayList("winner", response.body());
                Fragment fragment = new RecipeWinnerFragment();
                fragment.setArguments(args);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                        replace(R.id.content, fragment).addToBackStack(null).commit();

            }

            @Override
            public void onFailure(Call<ArrayList<Winner>> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
