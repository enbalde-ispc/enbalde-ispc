package com.ar.enbaldeapp.services;

import com.ar.enbaldeapp.models.utilities.JsonUtilities;
import com.google.gson.JsonObject;

public class ApiError {
    private final String message;
    private final int status;
    private final Object data;

    public ApiError(String text) {
        this.message = text;
        this.status = 0;
        this.data = null;
    }

    public ApiError(JsonObject json, int status) {
        ServerApiResponse error = JsonUtilities.getConfiguredGson().fromJson(json, ServerApiResponse.class);
        if (error.getMessage() != null) {
            this.message = error.getMessage();
            this.status = error.getStatus();
            this.data = error.getData();
        }
        else {
            DjangoApiResponse djangoError = JsonUtilities.getConfiguredGson().fromJson(json, DjangoApiResponse.class);
            this.message = djangoError.getMessage();
            this.status = status;
            this.data = null;
        }
    }

    public String getMessage() { return message; }

    public int getStatus() { return status; }
}