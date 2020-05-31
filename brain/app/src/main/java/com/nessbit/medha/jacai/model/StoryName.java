package com.nessbit.medha.jacai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StoryName implements Parcelable {

    @SerializedName("storyName")
    private String storyName;

    protected StoryName(Parcel in) {
        storyName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storyName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoryName> CREATOR = new Creator<StoryName>() {
        @Override
        public StoryName createFromParcel(Parcel in) {
            return new StoryName(in);
        }

        @Override
        public StoryName[] newArray(int size) {
            return new StoryName[size];
        }
    };

    public String getStoryName() {
        return storyName;
    }
}
