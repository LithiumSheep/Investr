package group15.oose.investmenttinder;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import group15.oose.investmenttinder.api.models.response_models.LoginResponse;

public class UserPreferences {

    private static final String PREFERENCES_NAME = "user";
    private static String USER_DATA = "user_data";


    private SharedPreferences preferences;
    private Gson gson;

    public UserPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        gson = new Gson();
    }

    public void saveLoggedUser(LoginResponse loggedUser) {
        preferences.edit().putString(USER_DATA, gson.toJson(loggedUser)).commit();
    }

    public LoginResponse getLoggedUser() {
        return gson.fromJson(preferences.getString(USER_DATA, ""), LoginResponse.class);
    }

    public void clear() {
        preferences.edit().clear().commit();
    }
}
