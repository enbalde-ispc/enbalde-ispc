package com.ar.enbaldeapp.services;

import com.ar.enbaldeapp.models.utilities.JsonUtilities;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.List;

public class ApiResponse<T> {
    private final String message;
    private final Object data;
    private final int status;

    public ApiResponse(String jsonText) {
        ServerApiResponse response = JsonUtilities.getConfiguredGson().fromJson(jsonText, ServerApiResponse.class);
        this.message = response.getMessage();
        this.data = response.getData();
        this.status = response.getStatus();
    }

    public T castResponseAs(Class<T> typeParameterClass) {
        JsonObject object = JsonUtilities.getConfiguredGson().toJsonTree(this.data).getAsJsonObject();
        return JsonUtilities.getConfiguredGson().fromJson(object, typeParameterClass);
    }

    public List<T> castResponseAsListOf(Type type) {
        JsonArray object = JsonUtilities.getConfiguredGson().toJsonTree(this.data).getAsJsonArray();
        return JsonUtilities.getConfiguredGson().fromJson(object, type);
    }

    public String getMessage() { return message; }

    public int getStatus() { return this.status; }

    public Object getRawData() {
        return this.data;
    }
}
