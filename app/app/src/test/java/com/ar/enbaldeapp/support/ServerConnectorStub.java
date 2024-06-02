package com.ar.enbaldeapp.support;

import com.ar.enbaldeapp.services.ApiError;
import com.ar.enbaldeapp.services.ApiResponse;
import com.ar.enbaldeapp.services.IServerConnector;

public class ServerConnectorStub<T> implements IServerConnector<T> {
    private boolean connect;
    private ApiResponse<T> response;
    private ApiError error;

    public static class Builder<T> {
        private boolean connect;
        private ApiResponse<T> response;
        private ApiError error;

        public Builder<T> withConnectReturning(boolean connect) {
            this.connect = connect;
            return this;
        }

        public Builder<T> withResponse(ApiResponse<T> response) {
            this.response = response;
            return this;
        }

        public Builder<T> withError(ApiError error) {
            this.error = error;
            return this;
        }

        public IServerConnector<T> build() {
            return new ServerConnectorStub<>(this.connect, this.response, this.error);
        }
    }

    private ServerConnectorStub(boolean connect, ApiResponse<T> response, ApiError error) {

        this.connect = connect;
        this.response = response;
        this.error = error;
    }

    @Override
    public boolean connect() {
        return this.connect;
    }

    @Override
    public ApiResponse<T> getResponse() {
        return this.response;
    }

    @Override
    public ApiError getError() {
        return this.error;
    }
}
