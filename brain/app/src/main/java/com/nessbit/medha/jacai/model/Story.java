package com.nessbit.medha.jacai.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Story implements Parcelable {

    @SerializedName("storyId")
    private int storyId;

    @SerializedName("storyName")
    private String storyName;

    @SerializedName("storyDetails")
    private String storyDetails;

    @SerializedName("storyImage")
    private String storyImage;

    @SerializedName("questions")
    private ArrayList<Question> questions;

    protected Story(Parcel in) {
        storyId = in.readInt();
        storyName = in.readString();
        storyDetails = in.readString();
        storyImage = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(storyId);
        dest.writeString(storyName);
        dest.writeString(storyDetails);
        dest.writeString(storyImage);
        dest.writeTypedList(questions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public int getStoryId() {
        return storyId;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getStoryDetails() {
        return storyDetails;
    }

    public String getStoryImage() {
        return storyImage;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
