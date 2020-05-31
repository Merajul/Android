package com.nessbit.medha.jacai.service.parser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.nessbit.medha.jacai.R;
import com.nessbit.medha.jacai.model.Question;
import com.nessbit.medha.jacai.model.Story;
import com.nessbit.medha.jacai.service.network.NetworkClient;
import com.nessbit.medha.jacai.service.network.NetworkHelper;
import com.nessbit.medha.jacai.view.activity.StoryActivity;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.medha.jacai.utils.DialogUtils.showFetchingDialog;

public class FetchStory {

    private Context context;

    public FetchStory(Context context) {
        this.context = context;
    }

    public void execute(String storyName) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<Story> call = networkHelper.getStory(storyName);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                spotsDialog.dismiss();
                ArrayList<Question> questions = response.body().getQuestions();
                context.startActivity(new Intent(context, StoryActivity.class).putExtra("story", response.body())
                        .putExtra("question", questions));
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                spotsDialog.dismiss();
                alertDialog.setMessage(context.getResources().getString(R.string.networkResult));
                alertDialog.show();
            }
        });
    }
}
