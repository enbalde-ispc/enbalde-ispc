package com.ar.enbaldeapp.services.wrappers;

import com.ar.enbaldeapp.models.utilities.HttpUtilities;

public class ApiResponseWrapper implements IResponseWrapper {
    @Override
    public String preprocessResponse(int statusCode, String response) {
        return "{ \"status\": " + statusCode + ", \"mensaje\": \"" + (HttpUtilities.isSuccessful(statusCode) ? "Éxito" : "Error") + "\", \"data\": " + response + " }";
    }
}

