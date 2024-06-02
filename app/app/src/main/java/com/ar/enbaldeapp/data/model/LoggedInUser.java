package com.ar.enbaldeapp.data.model;

import com.ar.enbaldeapp.models.TokenResponse;
import com.ar.enbaldeapp.models.User;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private final User model;
    private final long cartId;
    private final String access;
    private final String refresh;

    public LoggedInUser(User user, long cartId, TokenResponse tokenResponse) {
        this.model = user;
        this.cartId = cartId;
        this.access = tokenResponse.getAccess();
        this.refresh = tokenResponse.getRefresh();
    }

    public User getModel() { return model; }
    public long getCartId() { return cartId; }
    public String getAccess() { return this.access; }
    public String getRefresh() { return this.refresh; }
}