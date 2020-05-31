package com.nessbit.vojonbari.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Winner implements Parcelable {

    @SerializedName("winnerName")
    private String winnerName;

    @SerializedName("winnerRecipe")
    private String winnerRecipe;

    @SerializedName("winnerMonth")
    private String winnerMonth;

    @SerializedName("winnerScore")
    private String winnerScore;

    protected Winner(Parcel in) {
        winnerName = in.readString();
        winnerRecipe = in.readString();
        winnerMonth = in.readString();
        winnerScore = in.readString();
    }

    public static final Creator<Winner> CREATOR = new Creator<Winner>() {
        @Override
        public Winner createFromParcel(Parcel in) {
            return new Winner(in);
        }

        @Override
        public Winner[] newArray(int size) {
            return new Winner[size];
        }
    };

    public String getWinnerName() {
        return winnerName;
    }

    public String getWinnerRecipe() {
        return winnerRecipe;
    }

    public String getWinnerMonth() {
        return winnerMonth;
    }

    public String getWinnerScore() {
        return winnerScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(winnerName);
        dest.writeString(winnerRecipe);
        dest.writeString(winnerMonth);
        dest.writeString(winnerScore);
    }
}
