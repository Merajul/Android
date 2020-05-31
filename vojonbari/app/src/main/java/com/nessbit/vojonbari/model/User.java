package com.nessbit.vojonbari.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private String id;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userMobile")
    private String userMobile;

    @SerializedName("age")
    private String age;

    @SerializedName("status")
    private String status;

    @SerializedName("gender")
    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
