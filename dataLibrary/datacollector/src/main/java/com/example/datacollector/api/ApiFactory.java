package com.example.datacollector.api;

import android.support.annotation.NonNull;

public final class ApiFactory {

    private static ApiProvider provider;

    @NonNull
    public static ApiProvider getProvider() {
        return provider;
    }

    public static void setProvider(@NonNull ApiProvider provider) {
        ApiFactory.provider = provider;
    }
}