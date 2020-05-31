package com.nessbit.vojonbari.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeCategory implements Parcelable {

    @SerializedName("category")
    private String category;

    public String getCategory() {
        return category;
    }

    protected RecipeCategory(Parcel in) {
        category = in.readString();
    }

    public static final Creator<RecipeCategory> CREATOR = new Creator<RecipeCategory>() {
        @Override
        public RecipeCategory createFromParcel(Parcel in) {
            return new RecipeCategory(in);
        }

        @Override
        public RecipeCategory[] newArray(int size) {
            return new RecipeCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
    }
}
