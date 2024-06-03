package com.ar.enbaldeapp.services.wrappers;

public class DummyResponseWrapper implements IResponseWrapper {
    @Override
    public String preprocessResponse(int statusCode, String response) {
        return response;
    }
}