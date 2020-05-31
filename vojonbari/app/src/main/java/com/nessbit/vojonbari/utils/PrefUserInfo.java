package com.nessbit.vojonbari.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.nessbit.vojonbari.utils.AppConstants.PREF_IS_LOGGED_IN;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_KEY;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_LOCATION_SEND;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_AGE;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_GENDER;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_ID;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_MOB;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_NAME;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_USER_STATUS;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_WINNER_MONTH;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_WINNER_NAME;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_WINNER_RECIPE;
import static com.nessbit.vojonbari.utils.AppConstants.PREF_WINNER_SCORE;

public class PrefUserInfo {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public PrefUserInfo(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean tf){
        editor.putBoolean(PREF_IS_LOGGED_IN, tf);
        editor.apply();
    }

    public boolean getLoggedIn(){
        return sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false);
    }

    public void setUserId(String id){
        editor.putString(PREF_USER_ID, id);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(PREF_USER_ID, "");
    }

    public void setUserName(String name){
        editor.putString(PREF_USER_NAME, name);
        editor.apply();
    }

    public String getUserName(){
        return sharedPreferences.getString(PREF_USER_NAME, "");
    }

    public void setUserMobile(String mobile){
        editor.putString(PREF_USER_MOB, mobile);
        editor.apply();
    }

    public String getUserMobile(){
        return sharedPreferences.getString(PREF_USER_MOB, "");
    }

    public void setUserAge(String age){
        editor.putString(PREF_USER_AGE, age);
        editor.apply();
    }

    public String getUserAge(){
        return sharedPreferences.getString(PREF_USER_AGE, "");
    }

    public void setUserStatus(String status){
        editor.putString(PREF_USER_STATUS, status);
        editor.apply();
    }

    public String getUserStatus(){
        return sharedPreferences.getString(PREF_USER_STATUS, "");
    }

    public void setUserGender(String gender){
        editor.putString(PREF_USER_GENDER, gender);
        editor.apply();
    }

    public String getUserGender(){
        return sharedPreferences.getString(PREF_USER_GENDER, "");
    }

    public void setLocationSend(boolean tf){
        editor.putBoolean(PREF_LOCATION_SEND, tf);
        editor.apply();
    }

    public boolean getLocationSend(){
        return sharedPreferences.getBoolean(PREF_LOCATION_SEND, false);
    }

    public void setWinnerMonth(int month){
        editor.putInt(PREF_WINNER_MONTH, month);
        editor.apply();
    }

    public int getWinnerMonth(){
        return sharedPreferences.getInt(PREF_WINNER_MONTH, 0);
    }

    public void setWinnerScore(String score){
        editor.putString(PREF_WINNER_SCORE, score);
        editor.apply();
    }

    public String getWinnerScore(){
        return sharedPreferences.getString(PREF_WINNER_SCORE, "");
    }

    public void setWinnerName(String name){
        editor.putString(PREF_WINNER_NAME, name);
        editor.apply();
    }

    public String  getWinnerName(){
        return sharedPreferences.getString(PREF_WINNER_NAME, "");
    }

    public void setWinnerRecipe(String recipe){
        editor.putString(PREF_WINNER_RECIPE, recipe);
        editor.apply();
    }

    public String  getWinnerRecipe(){
        return sharedPreferences.getString(PREF_WINNER_RECIPE, "");
    }
}
