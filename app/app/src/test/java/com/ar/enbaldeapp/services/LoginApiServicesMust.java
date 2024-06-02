package com.ar.enbaldeapp.services;

import static com.ar.enbaldeapp.support.Constants.LOGIN_OK_JSON;
import static com.ar.enbaldeapp.support.Constants.PASSWORD;
import static com.ar.enbaldeapp.support.Constants.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.enbaldeapp.models.UserToken;
import com.ar.enbaldeapp.support.ApiServicesStub;
import com.ar.enbaldeapp.support.ServerConnectorStub;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(Theories.class)
public class LoginApiServicesMust {
    @Test
    public void throwException_whenSuccessCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.login(USERNAME, PASSWORD, null, e -> {}));
        assertEquals("El callback por éxito es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenFailureCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.login(USERNAME, PASSWORD, u -> {}, null));
        assertEquals("El callback por fallo es inválido", exception.getMessage());
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @Theory
    public void callFailureCallback_whenUsernameIsInvalid(@FromDataPoints("invalid strings") String invalidUsername) {
        AtomicBoolean errorCalled = new AtomicBoolean(false);
        ApiServices sut = new ApiServices();
        sut.login(invalidUsername, PASSWORD, s -> {}, e -> errorCalled.set(true));
        assertTrue(errorCalled.get());
    }

    @Theory
    public void callFailureCallback_whenPasswordIsInvalid(@FromDataPoints("invalid strings") String invalidPassword) {
        AtomicBoolean errorCalled = new AtomicBoolean(false);
        ApiServices sut = new ApiServices();
        sut.login(USERNAME, invalidPassword, s -> {}, e -> errorCalled.set(true));
        assertTrue(errorCalled.get());
    }

    @Test
    public void callFailureCallback_whenConnectFails() {
        AtomicBoolean errorCalled = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserTokenFromCallback((u, s) -> new ServerConnectorStub.Builder<UserToken>()
                        .withConnectReturning(false)
                        .build()
                )
                .build();

        sut.login(USERNAME, PASSWORD, u -> {}, e -> errorCalled.set(true));
        assertTrue(errorCalled.get());
    }

    @Test
    public void callSuccessCallback_whenConnectIsOk() {
        AtomicBoolean successCalled = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserTokenFromCallback((u, s) -> new ServerConnectorStub.Builder<UserToken>()
                        .withConnectReturning(true)
                        .withResponse(new ApiResponse<>(LOGIN_OK_JSON))
                        .build()
                )
                .build();

        sut.login(USERNAME, PASSWORD, u -> successCalled.set(true), e -> {});
        assertTrue(successCalled.get());
    }
}
