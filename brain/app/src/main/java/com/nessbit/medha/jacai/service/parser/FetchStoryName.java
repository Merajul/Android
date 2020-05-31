package com.nessbit.medha.jacai.service.parser;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.StoryName;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.view.fragment.StoryNameFragment;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.medha.jacai.utils.DialogUtils.showFetchingDialog;

public class FetchStoryName {

    private Context context;

    public FetchStoryName(Context context) {
        this.context = context;
    }

    public void execute(String category) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<ArrayList<StoryName>> call = networkHelper.getStoryName(category);
        call.enqueue(new Callback<ArrayList<StoryName>>() {
            @Override
            public void onResponse(Call<ArrayList<StoryName>> call, Response<ArrayList<StoryName>> response) {
                spotsDialog.dismiss();
                if ("".equalsIgnoreCase(response.body().get(0).getStoryName())) {
                    alertDialog.setMessage("No story available with this category");
                    alertDialog.show();
                } else {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("storyName", response.body());
                    Fragment fragment = new StoryNameFragment();
                    fragment.setArguments(args);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                            replace(R.id.content, fragment).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StoryName>> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
