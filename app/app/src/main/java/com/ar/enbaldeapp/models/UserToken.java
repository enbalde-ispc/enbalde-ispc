package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

public class UserToken {
    @SerializedName("accessToken")
    private TokenResponse response;

    @SerializedName("usuarioActual")
    private User user;
    @SerializedName("carritoActual")
    private long cartId;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getCartId() {
        return this.cartId;
    }

    public TokenResponse getResponse() {
        return response;
    }

    public void setResponse(TokenResponse response) {
        this.response = response;
    }
}