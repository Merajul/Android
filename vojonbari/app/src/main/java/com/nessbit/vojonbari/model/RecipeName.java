package com.nessbit.vojonbari.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeName implements Parcelable {

    @SerializedName("recipeId")
    private String recipeId;

    @SerializedName("recipeName")
    private String recipeName;

    @SerializedName("avgRating")
    private String avgRating;

    @SerializedName("totalReviewer")
    private String totalReviewer;

    protected RecipeName(Parcel in) {
        recipeId = in.readString();
        recipeName = in.readString();
        avgRating = in.readString();
        totalReviewer = in.readString();
    }

    public static final Creator<RecipeName> CREATOR = new Creator<RecipeName>() {
        @Override
        public RecipeName createFromParcel(Parcel in) {
            return new RecipeName(in);
        }

        @Override
        public RecipeName[] newArray(int size) {
            return new RecipeName[size];
        }
    };

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public String getTotalReviewer() {
        return totalReviewer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeId);
        dest.writeString(recipeName);
        dest.writeString(avgRating);
        dest.writeString(totalReviewer);
    }
}
