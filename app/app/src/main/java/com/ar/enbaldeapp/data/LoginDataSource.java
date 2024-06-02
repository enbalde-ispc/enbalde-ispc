package com.ar.enbaldeapp.data;

import com.ar.enbaldeapp.data.model.LoggedInUser;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password) {
        try {
            final Result<LoggedInUser>[] result = new Result[1];
            IApiServices apiServices = new ApiServices();
            apiServices.login(username, password,
                    u -> result[0] = new Result.Success<>(new LoggedInUser(u.getUser(), u.getCartId(), u.getResponse())),
                    e -> result[0] = new Result.Error<>(new IOException("Could not log in: " + e.getMessage())));

            return result[0];
        } catch (Exception e) {
            return new Result.Error<LoggedInUser>(new IOException("Error logging in", e));
        }
    }

    // TODO: Consolidar logout de data source con logout de Profile fragment
    public Result<String> logout(String accessToken) {
        try {
            final Result<String>[] result = new Result[1];
            IApiServices apiServices = new ApiServices();
            apiServices.logout(accessToken,
                    s -> result[0] = new Result.Success<>(s),
                    e -> result[0] = new Result.Error<>(new IOException("Could not log out: " + e.getMessage())));

            return result[0];
        } catch (Exception e) {
            return new Result.Error<>(new IOException("Error logging out", e));
        }
    }
}