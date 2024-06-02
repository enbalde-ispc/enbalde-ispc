package com.ar.enbaldeapp.support;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.reply.IServerReply;
import com.ar.enbaldeapp.services.requesters.IRequester;

import java.io.IOException;

public class DummyRequester<T> implements IRequester<T> {
    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException {
    }

    @Override
    public String preprocessResponse(int statusCode, String response) {
        return response;
    }

    @Override
    public IServerReply<T> getReplyFromServer(IHttpUrlConnectionWrapper connection) throws IOException {
        return null;
    }
}
