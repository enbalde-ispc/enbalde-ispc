package com.ar.enbaldeapp.services;

import com.google.gson.annotations.SerializedName;

public class DjangoApiResponse {
    @SerializedName("detail")
    private String message;

    public String getMessage() {
        return message;
    }
}
