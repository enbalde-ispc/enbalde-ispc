package com.ar.enbaldeapp.services.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;

public interface IHttpUrlConnectionWrapper {
    void openFrom(URL url) throws IOException;
    int getResponseCode() throws IOException;
    InputStream getInputStream() throws IOException;
    InputStream getErrorStream();
    void disconnect();
    void setRequestMethod(String method) throws ProtocolException;
    void setDoOutput(boolean doOutput);
    void setDoInput(boolean doInput);
    void setRequestProperty(String key, String value);
    OutputStream getOutputStream() throws IOException;
}
