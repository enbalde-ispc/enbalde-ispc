package com.ar.enbaldeapp.services.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUrlConnectionWrapper implements IHttpUrlConnectionWrapper {
    private HttpURLConnection connection;

    @Override
    public void openFrom(URL url) throws IOException {
        connection = (HttpURLConnection)url.openConnection();
    }

    @Override
    public int getResponseCode() throws IOException {
        return this.connection.getResponseCode();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.connection.getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
        return this.connection.getErrorStream();
    }

    @Override
    public void disconnect() {
        this.connection.disconnect();
    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        this.connection.setRequestMethod(method);
    }

    @Override
    public void setDoOutput(boolean doOutput) {
        this.connection.setDoOutput(doOutput);
    }

    @Override
    public void setDoInput(boolean doInput) {
        this.connection.setDoInput(doInput);
    }

    @Override
    public void setRequestProperty(String key, String value) {
        this.connection.setRequestProperty(key, value);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.connection.getOutputStream();
    }
}
