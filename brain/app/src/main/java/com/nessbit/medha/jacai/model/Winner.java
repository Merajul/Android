package com.nessbit.medha.jacai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Winner implements Parcelable {

    @SerializedName("winnerId")
    private int winnerId;

    @SerializedName("winnerName")
    private String winnerName;

    @SerializedName("winnerMobile")
    private String winnerMobile;

    @SerializedName("winnerScore")
    private int winnerScore;

    protected Winner(Parcel in) {
        winnerId = in.readInt();
        winnerName = in.readString();
        winnerMobile = in.readString();
        winnerScore = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(winnerId);
        dest.writeString(winnerName);
        dest.writeString(winnerMobile);
        dest.writeInt(winnerScore);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getWinnerId() {
        return winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public String getWinnerMobile() {
        return winnerMobile;
    }

    public int getWinnerScore() {
        return winnerScore;
    }
}
