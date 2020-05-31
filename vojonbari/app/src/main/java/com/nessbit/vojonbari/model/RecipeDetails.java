package com.nessbit.vojonbari.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeDetails implements Parcelable {

    @SerializedName("recipeId")
    private String recipeId;

    @SerializedName("recipeName")
    private String recipeName;

    @SerializedName("recipeDetails")
    private String recipeDetails;

    @SerializedName("uploader")
    private String uploader;

    @SerializedName("recipeImage")
    private String recipeImage;

    protected RecipeDetails(Parcel in) {
        recipeId = in.readString();
        recipeName = in.readString();
        recipeDetails = in.readString();
        uploader = in.readString();
        recipeImage = in.readString();
    }

    public static final Creator<RecipeDetails> CREATOR = new Creator<RecipeDetails>() {
        @Override
        public RecipeDetails createFromParcel(Parcel in) {
            return new RecipeDetails(in);
        }

        @Override
        public RecipeDetails[] newArray(int size) {
            return new RecipeDetails[size];
        }
    };

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDetails() {
        return recipeDetails;
    }

    public String getUploader() {
        return uploader;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeId);
        dest.writeString(recipeName);
        dest.writeString(recipeDetails);
        dest.writeString(uploader);
        dest.writeString(recipeImage);
    }
}
