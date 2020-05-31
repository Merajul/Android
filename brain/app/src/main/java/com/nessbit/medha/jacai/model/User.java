package com.nessbit.medha.jacai.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private int id;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userMobile")
    private String userMobile;

    @SerializedName("age")
    private int age;

    @SerializedName("gender")
    private String gender;

    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getStatus() {
        return status;
    }
}
