package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.reply.IServerReply;

import java.io.IOException;

public interface IRequester<T> {
    void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException;
    String preprocessResponse(int statusCode, String response);
    IServerReply<T> getReplyFromServer(IHttpUrlConnectionWrapper connection) throws IOException;
}
