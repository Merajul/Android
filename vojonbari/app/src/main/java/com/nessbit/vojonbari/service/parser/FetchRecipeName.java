package com.nessbit.vojonbari.service.parser;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.RecipeName;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.view.fragment.RecipeNameFragment;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showFetchingDialog;

public class FetchRecipeName {

    private Context context;

    public FetchRecipeName(Context context) {
        this.context = context;
    }

    public void execute(String recipeCategory) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<ArrayList<RecipeName>> call = networkHelper.getRecipeName(recipeCategory);
        call.enqueue(new Callback<ArrayList<RecipeName>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeName>> call, Response<ArrayList<RecipeName>> response) {
                spotsDialog.dismiss();
                if ("".equalsIgnoreCase(response.body().get(0).getRecipeName())) {
                    alertDialog.setMessage("No recipe available with this Menu");
                    alertDialog.show();
                }else {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("recipeName", response.body());
                    Fragment fragment = new RecipeNameFragment();
                    fragment.setArguments(args);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                            replace(R.id.content, fragment).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeName>> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
