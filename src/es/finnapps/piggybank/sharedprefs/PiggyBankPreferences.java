package es.finnapps.piggybank.sharedprefs;

import com.google.inject.Inject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PiggyBankPreferences {

    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_AUTH_TOKEN = "auth_token";
    private static final String USER_NAME_TOKEN = "user_name";
    private static final String PASSWORD= "password";
    private static final String USER_PRETTY_NAME = "pretty_name";
    private static final String USER_REGISTERED = "user_registered";
    private static final String TOKEN = "user_token";

    private SharedPreferences prefs;

    @Inject
    public PiggyBankPreferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getToken (){
        return prefs.getString(TOKEN, "");
    }

    public void setToken(String token) {
        prefs.edit().putString(TOKEN, token).commit();
    }

    public String getUserPhone (){
        return prefs.getString(USER_PHONE_NUMBER, "");
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        prefs.edit().putString(USER_PHONE_NUMBER, userPhoneNumber).commit();
    }

    public String getPassword(){
        return prefs.getString(PASSWORD, "");
    }

    public void setPassword(String password) {
        prefs.edit().putString(PASSWORD, password).commit();
    }
    
    public String getUserName(){
        return prefs.getString(USER_NAME_TOKEN, "");
    }

    public void setUserName(String userAuthToken) {
        prefs.edit().putString(USER_NAME_TOKEN, userAuthToken).commit();
    }
    
    public String getUserAuthToken (){
        return prefs.getString(USER_AUTH_TOKEN, "");
    }

    public void setUserAuthToken(String userAuthToken) {
        prefs.edit().putString(USER_AUTH_TOKEN, userAuthToken).commit();
    }

    public String getUserPrettyName (){
        return prefs.getString(USER_PRETTY_NAME, "");
    }

    public void setUserPrettyName(String userPrettyName) {
        prefs.edit().putString(USER_PRETTY_NAME, userPrettyName).commit();
    }

    public boolean isUserRegistered (){
        return prefs.getBoolean(USER_REGISTERED, false);
    }

    public void setUserRegistered(boolean userRegistered) {
        prefs.edit().putBoolean(USER_REGISTERED, userRegistered).commit();
    }
}
