package com.ar.enbaldeapp.services.wrappers;

import com.ar.enbaldeapp.models.utilities.HttpUtilities;
import com.ar.enbaldeapp.models.utilities.JsonUtilities;
import com.ar.enbaldeapp.services.DjangoApiResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DjangoApiResetPasswordResponseWrapper implements IResponseWrapper {
    private static final String PASSWORD_RESET_SUCCESS = "La clave ha sido cambiada con éxito. Ingrese con su nueva clave.";

    @Override
    public String preprocessResponse(int statusCode, String response) {
        String result = "";

        if (HttpUtilities.isSuccessful(statusCode)) {
            result = "{ \"status\": " + statusCode + ", \"mensaje\": \"" + PASSWORD_RESET_SUCCESS + "\", \"data\": " + response + " }";
        }
        else {
            Map<String, List<String>> tempMap = JsonUtilities.getConfiguredGson().fromJson(response, Map.class);
            if (tempMap.containsKey("detail")) {
                DjangoApiResponse apiResponse = JsonUtilities.getConfiguredGson().fromJson(response, DjangoApiResponse.class);
                result = "{ \"status\": " + statusCode + ", \"mensaje\": \"" + apiResponse.getMessage() + "\", \"data\": " + response + " }";
            }
            else {
                String message = tempMap.entrySet().stream()
                        .map(e -> e.getValue().stream().collect(Collectors.joining("\n")))
                        .collect(Collectors.joining("\n"));
                result = "{ \"status\": " + statusCode + ", \"mensaje\": \"" + message + "\", \"data\": " + response + " }";
            }
        }

        return result;
    }
}