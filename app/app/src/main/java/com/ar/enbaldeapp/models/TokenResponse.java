package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("acceso")
    private String access;

    @SerializedName("refresco")
    private String refresh;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}