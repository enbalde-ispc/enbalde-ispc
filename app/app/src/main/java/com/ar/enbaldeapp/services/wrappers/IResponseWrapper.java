package com.ar.enbaldeapp.services.wrappers;

public interface IResponseWrapper {
    String preprocessResponse(int statusCode, String response);
}
