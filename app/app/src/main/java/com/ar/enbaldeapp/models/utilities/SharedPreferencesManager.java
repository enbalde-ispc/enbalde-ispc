package com.ar.enbaldeapp.models.utilities;

import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_ACCESS;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_CART_ID;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_REFRESH;
import static com.ar.enbaldeapp.ui.IntentConstants.CURRENT_USER;

import android.content.Context;
import android.content.SharedPreferences;

import com.ar.enbaldeapp.models.User;

public class SharedPreferencesManager {
    private final String PREFS_NAME = "com.ar.enbaldeapp";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveCurrentUser(User user) {
        sharedPreferences.edit().putString(CURRENT_USER, JsonUtilities.getConfiguredGson().toJson(user)).apply();
    }

    public User loadCurrentUser() {
        String json = sharedPreferences.getString(CURRENT_USER, null);
        if (json == null) {
            return null;
        }

        return JsonUtilities.getConfiguredGson().fromJson(json, User.class);
    }

    public void deleteCurrentUser() {
        sharedPreferences.edit()
                .remove(CURRENT_USER)
                .remove(CURRENT_REFRESH)
                .remove(CURRENT_ACCESS)
                .remove(CURRENT_CART_ID)
                .apply();
    }

    public void saveCurrentRefresh(String refresh) {
        sharedPreferences.edit().putString(CURRENT_REFRESH, refresh).apply();
    }

    public void saveCurrentAccess(String access) {
        sharedPreferences.edit().putString(CURRENT_ACCESS, access).apply();
    }

    public void saveCurrentCartId(long cartId) {
        sharedPreferences.edit().putLong(CURRENT_CART_ID, cartId).apply();
    }

    public long getCurrentCartId() {
        return sharedPreferences.getLong(CURRENT_CART_ID, 0);
    }

    public String getAccessToken() {
        return sharedPreferences.getString(CURRENT_ACCESS, null);
    }
}
