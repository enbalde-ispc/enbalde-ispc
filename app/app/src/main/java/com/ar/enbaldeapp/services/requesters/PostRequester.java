package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.ApiRequest;
import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.wrappers.IResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class PostRequester<T> extends Requester<T> {
    private final ApiRequest request;
    private final IResponseWrapper responseWrapper;

    public PostRequester(ApiRequest request, IResponseWrapper responseWrapper) {
        this.request = request;
        this.responseWrapper = responseWrapper;
    }

    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(this.request.getData());
        out.close();
    }

    @Override
    public String preprocessResponse(int statusCode, String response) {
        return this.responseWrapper.preprocessResponse(statusCode, response);
    }
}
