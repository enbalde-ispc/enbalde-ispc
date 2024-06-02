package com.ar.enbaldeapp.services;

import com.google.gson.annotations.SerializedName;

public class UserModificationRequest {
    @SerializedName("direccion")
    private final String address;
    @SerializedName("email")
    private final String email;
    @SerializedName("telefono")
    private final String phone;
    @SerializedName("observaciones")
    private final String observations;
    @SerializedName("clave")
    private final String password;

    public UserModificationRequest(String address, String email, String phone, String observations, String password) {
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.observations = observations;
        this.password = password;
    }
}
