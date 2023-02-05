package zxc.studio.vpt.utilities;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.util.Date;

import zxc.studio.vpt.models.User;

public class LocalStorageService {

    private static Context context;

    public LocalStorageService(Context context){
        this.context = context;
    }

    public void setUser(User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userClub",user.getUser_club());
        editor.putString("userCoach",user.getUser_coach());
        editor.putString("userEmail",user.getUser_email());
        editor.putString("userFirebaseID",user.getUser_firebase_id());
        editor.putString("userFirstName",user.getUser_firstName());
        editor.putString("userID",user.getUser_id());
        editor.putString("userLastName",user.getUser_lastName());
        editor.putString("userSex",user.getUser_sex());
        editor.putString("userSignUpDate",user.getUser_signup_date().toString());
        Log.d(TAG, "setUser: " + user.getUser_signup_date().toString());
        editor.commit();
    }

    public User getUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        String club = sharedPreferences.getString("userClub","");
        String coach = sharedPreferences.getString("userCoach","");
        String email = sharedPreferences.getString("userEmail","");
        String firebaseID = sharedPreferences.getString("userFirebaseID","");
        String firstName = sharedPreferences.getString("userFirstName","");
        String ID = sharedPreferences.getString("userID","");
        String lastName = sharedPreferences.getString("userLastName","");
        String sex = sharedPreferences.getString("userSex","");
        String signUpDateString = sharedPreferences.getString("userSignUpDate","");
        Date signUpDate = DateFunctions.dateFromLocallySavedString(signUpDateString);
        User user = new User(club,coach,email,firebaseID,firstName,ID,lastName,sex,signUpDate);
        return user;
    }

    public void setHeaderColour(String color){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("headerColor",color);
        sharedPreferences.edit().apply();
    }

    public int getHeaderColour(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        String headerColorString = sharedPreferences.getString("headerColor","#73D5FF");
        int headerColor = Color.parseColor(headerColorString);
        return headerColor;
    }

    public void setSubtractColour(String color){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("subtractColor",color);
        sharedPreferences.edit().apply();
    }

    public int getSubtractColour(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        String subtractColorString = sharedPreferences.getString("subtractColor","#73D5FF");
        int subtractColor = Color.parseColor(subtractColorString);
        return subtractColor;
    }

    public static long mostRecentAuthentication(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails",Context.MODE_PRIVATE);
        long mostRecent = sharedPreferences.getLong("authenticationLong",0);
        return mostRecent;
    }



}
