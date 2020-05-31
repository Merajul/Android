package com.nessbit.medha.jacai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Score implements Parcelable {

    @SerializedName("monthlyScore")
    private int monthlyScore;

    protected Score(Parcel in) {
        monthlyScore = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(monthlyScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    public int getMonthlyScore() {
        return monthlyScore;
    }
}
