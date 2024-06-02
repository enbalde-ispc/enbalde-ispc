package com.ar.enbaldeapp.ui;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Utilities {
    public static void changeBottomMenuToLogin(View view) {
        changeBottomMenuTo(view, "Login");
    }

    public static void changeBottomMenuToProfile(View view) {
        changeBottomMenuTo(view, "Profile");
    }

    public static void changeBottomMenuToRegistration(View view) {
        changeBottomMenuTo(view, "Registration");
    }

    public static void changeBottomMenuToRecover(View view) {
        changeBottomMenuTo(view, "Recovery");
    }

    private static void changeBottomMenuTo(View view, String text) {
        BottomNavigationView bottomNavigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(R.id.navigation_user);
        item.setTitle(text);
    }

    public static void showCartMenuItem(View view) {
        BottomNavigationView bottomNavigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(R.id.navigation_cart);
        item.setEnabled(true);
        item.setVisible(true);
    }

    public static void showPreviousOrdersMenuItem(View view) {
        BottomNavigationView bottomNavigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(R.id.navigation_history);
        item.setEnabled(true);
        item.setVisible(true);
    }

    public static void hideCartMenuItem(View view) {
        BottomNavigationView bottomNavigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(R.id.navigation_cart);
        item.setEnabled(false);
        item.setVisible(false);
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        return sharedPreferencesManager.loadCurrentUser() != null;
    }

    private static void changeToolbarTitleTo(Activity activity, String text) {
        ((AppCompatActivity)(activity)).getSupportActionBar().setTitle(text);
    }

    public static void changeToolbarTitleToLogin(Activity activity) {
        changeToolbarTitleTo(activity,"Login");
    }

    public static void changeToolbarTitleToProfile(Activity activity) {
        changeToolbarTitleTo(activity, "Profile");
    }

    public static void changeToolbarTitleToRegistration(Activity activity) {
        changeToolbarTitleTo(activity, "Registration");
    }

    public static void changeToolbarTitleToRecovery(Activity activity) {
        changeToolbarTitleTo(activity, "Recover Password");
    }
}
