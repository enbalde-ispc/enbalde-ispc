package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.wrappers.IResponseWrapper;

import java.io.IOException;

public class GetRequester<T> extends Requester<T> {
    private final IResponseWrapper wrapper;

    public GetRequester(IResponseWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException {
        connection.setRequestMethod("GET");
    }

    @Override
    public String preprocessResponse(int statusCode, String response) {
        return wrapper.preprocessResponse(statusCode, response);
    }
}