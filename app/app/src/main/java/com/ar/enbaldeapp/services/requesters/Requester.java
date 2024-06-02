package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.models.utilities.HttpUtilities;
import com.ar.enbaldeapp.services.reply.ErrorServerReply;
import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.reply.IServerReply;
import com.ar.enbaldeapp.services.reply.OkServerReply;

import java.io.IOException;

abstract class Requester<T> implements IRequester<T> {
    @Override
    public IServerReply<T> getReplyFromServer(IHttpUrlConnectionWrapper connection) throws IOException {
        return HttpUtilities.isSuccessful(connection.getResponseCode())
                ? new OkServerReply<T>(connection.getInputStream(), connection.getResponseCode(), this)
                : new ErrorServerReply<T>(connection.getErrorStream(), connection.getResponseCode(), this);
    }

    @Override
    public String preprocessResponse(int statusCode, String response) {
        return response;
    }
}