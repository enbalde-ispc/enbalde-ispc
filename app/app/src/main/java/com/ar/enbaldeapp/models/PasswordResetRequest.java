package com.ar.enbaldeapp.models;

public class PasswordResetRequest {
    private final String token;
    private final String password;

    public PasswordResetRequest(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}
