package com.ar.enbaldeapp.models.utilities;

public class HttpUtilities {
    public static boolean isSuccessful(int value) {
        return 200 <= value && value <= 299;
    }
}
