package com.ar.enbaldeapp.services;

import static com.ar.enbaldeapp.support.Constants.ADDRESS;
import static com.ar.enbaldeapp.support.Constants.EMAIL;
import static com.ar.enbaldeapp.support.Constants.FIRST_NAME;
import static com.ar.enbaldeapp.support.Constants.LAST_NAME;
import static com.ar.enbaldeapp.support.Constants.PASSWORD;
import static com.ar.enbaldeapp.support.Constants.PHONE;
import static com.ar.enbaldeapp.support.Constants.REGISTRATION_OK_JSON;
import static com.ar.enbaldeapp.support.Constants.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.support.ApiServicesStub;
import com.ar.enbaldeapp.support.ServerConnectorStub;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(Theories.class)
public class RegistrationApiServicesMust {
    @Test
    public void throwException_whenSuccessCallbackIsInvalid() {
        IApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, null, e -> {}));
        assertEquals("El callback por éxito es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenFailureCallbackIsInvalid() {
        IApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, null));
        assertEquals("El callback por fallo es inválido", exception.getMessage());
    }

    @Test
    public void callFailureCallback_whenConnectFails() {
        AtomicBoolean errorCalled = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserFromCallback((s, r) -> new ServerConnectorStub.Builder<User>()
                        .withConnectReturning(false)
                        .build())
                .build();

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, e -> errorCalled.set(true));
        assertTrue(errorCalled.get());
    }

    @Test
    public void callSuccessCallback_whenConnectWorks() {
        AtomicBoolean successCalled = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserFromCallback((s, r) -> new ServerConnectorStub.Builder<User>()
                        .withConnectReturning(true)
                        .withResponse(new ApiResponse<>(REGISTRATION_OK_JSON))
                        .build())
                .build();

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> successCalled.set(true), e -> {});
        assertTrue(successCalled.get());
    }

    @Test
    public void sendCorrectInformationToServer() {
        AtomicReference<String> data = new AtomicReference<>();
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserFromCallback((s, r) -> {
                    data.set(r.getData());
                    return new ServerConnectorStub.Builder<User>()
                            .withConnectReturning(false)
                            .build();
                    })
                .build();

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, e -> {});

        MatcherAssert.assertThat(data.get(), CoreMatchers.containsString(USERNAME));
        MatcherAssert.assertThat(data.get(), CoreMatchers.containsString(PASSWORD));
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @Theory
    public void doNotContactServer_whenFirstNameIsInvalid(@FromDataPoints("invalid strings") String invalidName) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(invalidName, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, e -> {});
        assertNull(request.get());
    }

    private static IApiServices getServicesCapturingRequest(AtomicReference<ApiRequest> request) {
        return new ApiServicesStub.Builder()
                .withGetUserFromCallback((s, r) -> {
                    request.set(r);
                    return new ServerConnectorStub.Builder<User>()
                            .withConnectReturning(false)
                            .build();
                })
                .build();
    }

    @Theory
    public void doNotContactServer_whenLastNameIsInvalid(@FromDataPoints("invalid strings") String invalidName) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(FIRST_NAME, invalidName, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, e -> {});
        assertNull(request.get());
    }

    @Theory
    public void doNotContactServer_whenEmailIsInvalid(@FromDataPoints("invalid strings") String invalidEmail) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(FIRST_NAME, LAST_NAME, invalidEmail, ADDRESS, PHONE, USERNAME, PASSWORD, u -> {}, e -> {});
        assertNull(request.get());
    }

    @Theory
    public void doNotContactServer_whenAddressIsInvalid(@FromDataPoints("invalid strings") String invalidAddress) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, invalidAddress, PHONE, USERNAME, PASSWORD, u -> {}, e -> {});
        assertNull(request.get());
    }

    @Theory
    public void doNotContactServer_whenUsernameIsInvalid(@FromDataPoints("invalid strings") String invalidUsername) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, invalidUsername, PASSWORD, u -> {}, e -> {});
        assertNull(request.get());
    }

    @Theory
    public void doNotContactServer_whenPasswordIsInvalid(@FromDataPoints("invalid strings") String invalidPassword) {
        AtomicReference<ApiRequest> request = new AtomicReference<>();
        IApiServices sut = getServicesCapturingRequest(request);

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, invalidPassword, u -> {}, e -> {});
        assertNull(request.get());
    }

    @Test
    public void deserializeServerReplyCorrectly() {
        AtomicReference<User> atomicUser = new AtomicReference<>();
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetUserFromCallback((s, r) -> new ServerConnectorStub.Builder<User>()
                        .withConnectReturning(true)
                        .withResponse(new ApiResponse<>(REGISTRATION_OK_JSON))
                        .build())
                .build();

        sut.register(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, PHONE, USERNAME, PASSWORD, atomicUser::set, e -> {});

        User user = atomicUser.get();
        assertEquals("Juan", user.getFirstName());
        assertEquals("Perez", user.getLastName());
        assertEquals("juan8", user.getUsername());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("11112222", user.getPhone());
        assertEquals("juan.perez@gmail.com", user.getEmail());
        assertEquals("", user.getObservations());
        assertEquals(User.Client, user.getType());
    }
}