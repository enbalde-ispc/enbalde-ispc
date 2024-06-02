package com.ar.enbaldeapp.services.reply;

import com.ar.enbaldeapp.services.ApiError;
import com.ar.enbaldeapp.services.ApiResponse;
import com.ar.enbaldeapp.services.IResponseCreator;
import com.ar.enbaldeapp.services.requesters.IRequester;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

public class ErrorServerReply<T> extends ServerReply<T> {
    private final InputStream inputStream;
    private final IRequester<T> requester;
    private final int status;

    public ErrorServerReply(InputStream inputStream, int status, IRequester<T> requester) {
        if (inputStream == null) {
            throw new RuntimeException("El input stream es inválido");
        }

        if (requester == null) {
            throw new RuntimeException("El requester es inválido");
        }

        this.inputStream = inputStream;
        this.requester = requester;
        this.status = status;
    }

    @Override
    public ApiResponse<T> getResponse() throws IOException {
        return null;
    }

    @Override
    public ApiError getError() throws IOException {
        String jsonText = this.loadInputFrom(this.inputStream);
        jsonText = this.requester.preprocessResponse(this.status, jsonText);

        return new ApiError(JsonParser.parseString(jsonText).getAsJsonObject(), status);
    }

    @Override
    public boolean getReturnValue() {
        return false;
    }
}
