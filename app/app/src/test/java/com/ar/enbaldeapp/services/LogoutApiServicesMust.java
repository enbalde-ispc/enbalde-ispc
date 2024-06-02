package com.ar.enbaldeapp.services;

import static com.ar.enbaldeapp.support.Constants.ACCESS_STRING;
import static com.ar.enbaldeapp.support.Constants.LOGOUT_ERROR_JSON;
import static com.ar.enbaldeapp.support.Constants.LOGOUT_OK_JSON;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_DESCRIPTION;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_ID;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_IMAGE;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_NAME;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_PRICE;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_QUANTITY;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_TYPE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.support.ApiServicesStub;
import com.ar.enbaldeapp.support.ServerConnectorStub;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;

import java.util.concurrent.atomic.AtomicBoolean;

public class LogoutApiServicesMust {
    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @Theory
    public void throwException_whenAccessStringIsInvalid(@FromDataPoints("invalid strings") String invalidToken) {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.logout(invalidToken, s -> {}, e -> {}));
        assertEquals("El access token es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenSuccessCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.logout(ACCESS_STRING, null, e -> {}));
        assertEquals("El callback por éxito es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenFailureCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.logout(ACCESS_STRING, u -> {}, null));
        assertEquals("El callback por fallo es inválido", exception.getMessage());
    }

    @Test
    public void callSuccess_whenLoggedOutCorrectly() {
        AtomicBoolean called = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withDisconnectFromCallback((u, s) -> new ServerConnectorStub.Builder<Boolean>()
                        .withConnectReturning(true)
                        .withResponse(new ApiResponse<>(LOGOUT_OK_JSON))
                        .build()
                )
                .build();

        sut.logout(ACCESS_STRING, s -> called.set(true), e -> {});
        assertTrue(called.get());
    }

    @Test
    public void callFailure_whenLoggedOutWithError() {
        AtomicBoolean called = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withDisconnectFromCallback((u, s) -> new ServerConnectorStub.Builder<Boolean>()
                        .withConnectReturning(true)
                        .withResponse(new ApiResponse<>(LOGOUT_ERROR_JSON))
                        .build()
                )
                .build();

        sut.logout(ACCESS_STRING, s -> {}, e -> called.set(true));
        assertTrue(called.get());
    }
}


