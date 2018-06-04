package com.example.datacollector.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.datacollector.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttp {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;

    private static OkHttpClient client = null;

    @NonNull
    public static OkHttpClient getClient(@NonNull Context context) {
        if (client == null) {
            create();
        }
        return client;
    }

    private static void create() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        clientBuilder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) clientBuilder.networkInterceptors().add(new StethoInterceptor());

        client = clientBuilder
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}