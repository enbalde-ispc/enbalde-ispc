package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;

import java.net.ProtocolException;

public class NoBodyRequester<T> extends Requester<T> {
    private final String token;

    public NoBodyRequester(String token) {
        this.token = token;
    }

    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws ProtocolException {
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
    }
}
