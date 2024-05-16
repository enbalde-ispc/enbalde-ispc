package com.ar.enbaldeapp.services;

import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.UserToken;

import java.util.function.Consumer;

public interface IApiServices {
    void login(String username, String password, Consumer<UserToken> onSuccess, Consumer<ApiError> onError);
    void logout(Consumer<String> onSuccess, Consumer<ApiError> onError);
    void register(String firstName, String lastName, String email, String address, String phoneNumber, String username, String password, Consumer<User> onSuccess, Consumer<ApiError> onError);
}
