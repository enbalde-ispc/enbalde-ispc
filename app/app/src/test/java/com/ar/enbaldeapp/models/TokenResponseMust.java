package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.ACCESS_STRING;
import static com.ar.enbaldeapp.support.Constants.REFRESH_STRING;
import static com.ar.enbaldeapp.support.Constants.TOKEN_REFRESH_JSON;
import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;

import org.junit.Test;

public class TokenResponseMust {
    @Test
    public void setAccessCorrectly() {
        TokenResponse sut = new TokenResponse();
        sut.setAccess(ACCESS_STRING);
        assertEquals(ACCESS_STRING, sut.getAccess());
    }

    @Test
    public void setRefreshCorrectly() {
        TokenResponse sut = new TokenResponse();
        sut.setRefresh(REFRESH_STRING);
        assertEquals(REFRESH_STRING, sut.getRefresh());
    }

    @Test
    public void deserializeTokenCorrectly() {
        TokenResponse sut = new Gson().fromJson(TOKEN_REFRESH_JSON, TokenResponse.class);
        assertEquals(REFRESH_STRING, sut.getRefresh());
        assertEquals(ACCESS_STRING, sut.getAccess());
    }

    @Test
    public void serializeTokenCorrectly() {
        TokenResponse sut = new TokenResponse();
        sut.setRefresh(REFRESH_STRING);
        sut.setAccess(ACCESS_STRING);
        String result = new Gson().toJson(sut);
        assertEquals(TOKEN_REFRESH_JSON, result);
    }
}
