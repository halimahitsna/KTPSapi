package id.sapi.ktp.aplikasiktpsapi.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import id.sapi.ktp.aplikasiktpsapi.modal.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "ktpsapi";

    public static final String KEY_USER_ID = "keyuserid";
    public static final String KEY_USER_NAME = "keynama";
    public static final String KEY_USER_USER = "keyuser";
    public static final String KEY_PASS = "keypass";
    public static final String KEY_LOGIN = "keySuccessLogin";
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        mCtx = context;
        sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_USER, user.getUser());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_USER, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_USER, null)
        );
    }
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String key7SP, boolean value){
        spEditor.putBoolean(key7SP, value);
        spEditor.commit();
    }
    public Boolean getSPSudahLogin(){
        return sp.getBoolean(KEY_LOGIN, false);
    }
    public Boolean getLogin(){
        return sp.getBoolean(KEY_LOGIN, false);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USER_NAME, sp.getString(KEY_USER_NAME, null));

        // user email id
        user.put(KEY_PASS, sp.getString(KEY_PASS, null));

        // return user
        return user;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
