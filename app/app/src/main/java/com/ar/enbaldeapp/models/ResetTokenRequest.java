package com.ar.enbaldeapp.models;

public class ResetTokenRequest {
    private final String email;

    public ResetTokenRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
