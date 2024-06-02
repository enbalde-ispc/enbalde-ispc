package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.ACCESS_STRING;
import static com.ar.enbaldeapp.support.Constants.ADDRESS;
import static com.ar.enbaldeapp.support.Constants.CART_ID;
import static com.ar.enbaldeapp.support.Constants.EMAIL;
import static com.ar.enbaldeapp.support.Constants.FIRST_NAME;
import static com.ar.enbaldeapp.support.Constants.LAST_NAME;
import static com.ar.enbaldeapp.support.Constants.OBSERVATIONS;
import static com.ar.enbaldeapp.support.Constants.PASSWORD;
import static com.ar.enbaldeapp.support.Constants.PHONE;
import static com.ar.enbaldeapp.support.Constants.REFRESH_STRING;
import static com.ar.enbaldeapp.support.Constants.TYPE;
import static com.ar.enbaldeapp.support.Constants.USERNAME;
import static com.ar.enbaldeapp.support.Constants.USER_ID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class UserTokenMust {
    @Test
    public void setCartIdCorrectly() {
        UserToken sut = new UserToken();
        sut.setCartId(CART_ID);
        assertEquals(CART_ID, sut.getCartId());
    }

    @Test
    public void setUserCorrectly() {
        User user = new User(USER_ID, LAST_NAME, FIRST_NAME, EMAIL, ADDRESS, PHONE, OBSERVATIONS, USERNAME, PASSWORD, TYPE);
        UserToken sut = new UserToken();
        sut.setUser(user);
        assertSame(user, sut.getUser());
    }

    @Test
    public void setTokenCorrectly() {
        TokenResponse response = new TokenResponse();
        response.setAccess(ACCESS_STRING);
        response.setRefresh(REFRESH_STRING);

        UserToken sut = new UserToken();
        sut.setResponse(response);
        assertSame(response, sut.getResponse());
    }
}
