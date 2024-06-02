package com.ar.enbaldeapp.services;

import com.google.gson.annotations.SerializedName;

class ServerApiResponse {
    @SerializedName("mensaje")
    private String message;
    private Object data;
    private int status;

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
