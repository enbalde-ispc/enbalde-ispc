package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class PasswordResetResponse {
    private final String status;

    public PasswordResetResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}


