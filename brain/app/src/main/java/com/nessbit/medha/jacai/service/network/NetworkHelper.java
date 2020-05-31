package com.nessbit.medha.jacai.service.network;

import com.nessbit.medha.jacai.model.Category;
import com.nessbit.medha.jacai.model.GetResponse;
import com.nessbit.medha.jacai.model.Score;
import com.nessbit.medha.jacai.model.Story;
import com.nessbit.medha.jacai.model.StoryName;
import com.nessbit.medha.jacai.model.User;
import com.nessbit.medha.jacai.model.Winner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkHelper {

    @FormUrlEncoded
    @POST("registration.php")
    Call<GetResponse> registration(@Field("user_name") String name, @Field("mobile") String mobile,
                                   @Field("age") String age, @Field("gender") String gender, @Field("device") String device);

    @GET("login.php")
    Call<User> getUserInfo(@Query("mobile") String mobile);

    @GET("FetchCategory.php")
    Call<ArrayList<Category>> getCategory();

    @GET("FetchStoryName.php")
    Call<ArrayList<StoryName>> getStoryName(@Query("category") String category);

    @GET("FetchStory.php")
    Call<Story> getStory(@Query("storyName") String storyName);

    @GET("FetchScore.php")
    Call<Score> getScore(@Query("userId") int userId);

    @GET("FetchWinner.php")
    Call<Winner> getWinner();

    @FormUrlEncoded
    @POST("UploadScore.php")
    Call<GetResponse> uploadScore(@Field("userId") int userId, @Field("score") int score);

    @FormUrlEncoded
    @POST("UpdateUser.php")
    Call<GetResponse> updateUser(@Field("userId") int userId,@Field("userName") String userName, @Field("age") int age);
}
