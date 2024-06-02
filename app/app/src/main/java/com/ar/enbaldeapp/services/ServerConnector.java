package com.ar.enbaldeapp.services;

import android.os.Handler;
import android.os.Looper;

import com.ar.enbaldeapp.services.connection.IHttpUrlConnectionWrapper;
import com.ar.enbaldeapp.services.reply.IServerReply;
import com.ar.enbaldeapp.services.requesters.IRequester;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

class ServerConnector<T> implements IServerConnector<T> {
    private URL url;
    private ApiResponse<T> response;
    private ApiError error;
    private final IRequester<T> requester;
    private final Callable<IHttpUrlConnectionWrapper> connectionCreator;

    public ServerConnector(String urlString, IRequester<T> requester, Callable<IHttpUrlConnectionWrapper> connectionCreator) {
        if (!StringToUrl(urlString)) {
            throw new RuntimeException("Invalid url " + urlString);
        }

        this.connectionCreator = connectionCreator;
        this.requester = requester;
    }

    @Override
    public boolean connect() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        AtomicBoolean result = new AtomicBoolean(false);
        executor.execute(() -> {
            result.set(load());
        });

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException ex) {
            return false;
        }

        return result.get();
    }

    @Override
    public ApiResponse<T> getResponse() { return this.response; }

    @Override
    public ApiError getError() { return this.error; }

    private boolean load() {
        IHttpUrlConnectionWrapper connection = null;

        try {
            connection = this.connectionCreator.call();
            connection.openFrom(url);

            this.requester.sendRequestTo(connection);
            IServerReply<T> serverReply = this.requester.getReplyFromServer(connection);

            this.error = serverReply.getError();
            this.response = serverReply.getResponse();

            return serverReply.getReturnValue();
        } catch (IOException ex) {
            error = new ApiError(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return false;
    }

    private boolean StringToUrl(String urlString) {
        try {
            url = new URL(urlString);
            return true;
        } catch (MalformedURLException ex) {
            error = new ApiError(ex.getMessage());
        }

        return false;
    }
}

