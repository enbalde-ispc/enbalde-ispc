package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.wrappers.IResponseWrapper;

import java.io.IOException;

public class AuthenticatedGetRequester<T> extends GetRequester<T> {
    private final String accessToken;

    public AuthenticatedGetRequester(IResponseWrapper wrapper, String accessToken) {
        super(wrapper);
        this.accessToken = accessToken;
    }

    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException {
        super.sendRequestTo(connection);
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
    }
}
