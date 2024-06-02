package com.ar.enbaldeapp.support;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUrlConnectionWrapperStub implements IHttpUrlConnectionWrapper {
    @Override
    public void openFrom(URL url) throws IOException {

    }

    @Override
    public int getResponseCode() throws IOException {
        return 0;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {

    }

    @Override
    public void setDoOutput(boolean doOutput) {

    }

    @Override
    public void setDoInput(boolean doInput) {

    }

    @Override
    public void setRequestProperty(String key, String value) {

    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }
}
