package com.ar.enbaldeapp.data;

import android.content.Context;

import com.ar.enbaldeapp.data.model.LoggedInUser;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.utilities.SharedPreferencesManager;
import com.ar.enbaldeapp.services.ApiServices;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;
    private String refresh;
    private String access;
    private long cartId;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        dataSource.logout(access);
        user = null;
        access = null;
        refresh = null;
        cartId = -1;
    }

    private void setLoggedInUser(User user, Context context) {
        this.user = user;
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.saveCurrentUser(user);
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password, Context context) {
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>) result).getData();

            if(loggedInUser.getModel().getType() == 1) {
                return result = new Result.Error<>("admin not allowed");
            }

            Result.Success<LoggedInUser> success = (Result.Success<LoggedInUser>)result;
            setLoggedInUser(success.getData().getModel(), context);
            setRefresh(success.getData().getRefresh(), context);
            setAccess(success.getData().getAccess(), context);
            setCartId(success.getData().getCartId(), context);
        }
        return result;
    }

    private void setRefresh(String refresh, Context context) {
        this.refresh = refresh;
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.saveCurrentRefresh(this.refresh);
    }

    private void setAccess(String access, Context context) {
        this.access = access;
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.saveCurrentAccess(this.access);
    }

    private void setCartId(long cartId, Context context) {
        this.cartId = cartId;
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferencesManager.saveCurrentCartId(this.cartId);
    }
}