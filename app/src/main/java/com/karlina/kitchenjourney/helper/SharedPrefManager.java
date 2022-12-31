package com.karlina.kitchenjourney.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.karlina.kitchenjourney.LoginActivity;
import com.karlina.kitchenjourney.model.User;

public class SharedPrefManager {

    //the constants
    public static final String SHARED_PREF_NAME = "kjPref";
    public static final String KEY_RESEP = "kjResep";
    private static final String KEY_ID = "kj_id";
    private static final String KEY_NAME = "kj_name";
    private static final String KEY_EMAIL = "kj_email";
    private static final String KEY_KEY = "kj_key";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_KEY, user.getKey());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_KEY, null)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
        editor.putInt(KEY_ID, 0);
        editor.putString(KEY_NAME, null);
        editor.putString(KEY_EMAIL, null);
        editor.putString(KEY_KEY, null);
        editor.apply();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
    }
}
