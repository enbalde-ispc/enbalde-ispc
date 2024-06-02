package com.ar.enbaldeapp.services.requesters;

import com.ar.enbaldeapp.services.ApiRequest;
import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class PostFormDataRequester<T> extends Requester<T> {
    private final ApiRequest request;

    public PostFormDataRequester(ApiRequest request) {
        this.request = request;
    }

    @Override
    public void sendRequestTo(IHttpUrlConnectionWrapper connection) throws IOException {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.request.getBoundary());

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(this.request.getData());
        out.close();
    }
}



