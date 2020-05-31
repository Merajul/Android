package com.nessbit.vojonbari.service;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.nessbit.vojonbari.R;
import com.nessbit.vojonbari.model.GetResponse;
import com.nessbit.vojonbari.service.network.NetworkClient;
import com.nessbit.vojonbari.service.network.NetworkHelper;
import com.nessbit.vojonbari.utils.PrefUserInfo;

import java.io.File;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nessbit.vojonbari.utils.DialogUtils.showFetchingDialog;

public class UploadRecipeService {
    private static final String TAG = "UploadRecipeService";
    private Context context;
    private PrefUserInfo prefUserInfo;

    public UploadRecipeService(Context context) {
        this.context = context;
        prefUserInfo = new PrefUserInfo(context);
    }

    public void execute(String recipeCategory, String recipeName, String recipeDetails, String filePath) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), recipeCategory);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), recipeName);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), recipeDetails);
        RequestBody uploader = RequestBody.create(MediaType.parse("text/plain"), prefUserInfo.getUserName());

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<GetResponse> call = networkHelper.uploadRecipe(category,name,description,uploader,part);
        call.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                spotsDialog.dismiss();
                if (response.body().getResponse().equalsIgnoreCase("recipe_uploaded"))
                    alertDialog.setMessage("Recipe Uploaded");
                else alertDialog.setMessage("Recipe Upload Failed");
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

    public void executeAlternative(String recipeCategory, String recipeName, String recipeDetails, String noImage) {
        SpotsDialog spotsDialog = showFetchingDialog(context);
        spotsDialog.show();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.networkTitle));

        Log.i(TAG, "executeAlternative: "+noImage);

        NetworkHelper networkHelper = NetworkClient.newNetworkClient().create(NetworkHelper.class);
        Call<GetResponse> call = networkHelper.uploadRecipeAlternative(
                recipeCategory,recipeName,recipeDetails,prefUserInfo.getUserName(),noImage);
        call.enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                spotsDialog.dismiss();
                if (response.body().getResponse().equalsIgnoreCase("recipe_uploaded"))
                    alertDialog.setMessage("Recipe Uploaded");
                else alertDialog.setMessage("Recipe Upload Failed");
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
