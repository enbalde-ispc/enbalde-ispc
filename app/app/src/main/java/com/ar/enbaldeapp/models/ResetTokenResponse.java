package com.ar.enbaldeapp.models;

public class ResetTokenResponse {
    private final String status;

    public ResetTokenResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

