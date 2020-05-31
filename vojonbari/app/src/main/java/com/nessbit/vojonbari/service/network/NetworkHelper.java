package com.nessbit.vojonbari.service.network;


import com.nessbit.vojonbari.model.GetResponse;
import com.nessbit.vojonbari.model.RecipeCategory;
import com.nessbit.vojonbari.model.RecipeDetails;
import com.nessbit.vojonbari.model.RecipeName;
import com.nessbit.vojonbari.model.User;
import com.nessbit.vojonbari.model.Winner;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface NetworkHelper {
    @FormUrlEncoded
    @POST("registration.php")
    Call<GetResponse> registration(@Field("user_name") String name, @Field("mobile") String mobile,
                                   @Field("age") String age, @Field("gender") String gender, @Field("device") String device);

    @GET("login.php")
    Call<List<User>> getUserInfo(@Query("mobile") String mobile);

    @FormUrlEncoded
    @POST("review.php")
    Call<GetResponse> review(@Field("recipeId") String recipeId, @Field("userId") String userId,
                             @Field("reviewerName") String reviewerName, @Field("rating") String rating,
                             @Field("reviewText") String reviewText);

    @Multipart
    @POST("UploadRecipe.php")
    Call<GetResponse> uploadRecipe(@Part("recipeCategory") RequestBody recipeCategory, @Part("recipeName") RequestBody recipeName,
                                   @Part("recipeDetails") RequestBody recipeDetails, @Part("uploader") RequestBody uploader,
                                   @Part MultipartBody.Part recipeImage);

    @FormUrlEncoded
    @POST("UploadRecipeAlternative.php")
    Call<GetResponse> uploadRecipeAlternative(@Field("recipeCategory") String recipeCategory, @Field("recipeName") String recipeName,
                                              @Field("recipeDetails") String recipeDetails, @Field("uploader") String uploader,
                                              @Field("noImage") String recipeImage);

    @GET("FetchRecipeCategory.php")
    Call<ArrayList<RecipeCategory>> getRecipeCategory();

    @GET("FetchRecipeName.php")
    Call<ArrayList<RecipeName>> getRecipeName(@Query("category") String recipeCategory);

    @GET("FetchRecipeDetails.php")
    Call<ArrayList<RecipeDetails>> getRecipeDetails(@Query("recipeId") String recipeId);

    @GET("FetchWinner.php")
    Call<ArrayList<Winner>> getWinner(@Query("winnerMonth") int winnerMonth,@Query("desiredMonth") int desiredMonth);
}
