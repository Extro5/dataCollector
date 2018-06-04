package com.example.datacollector.api;

import android.content.Context;
import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultApiProvider implements ApiProvider {

    private DataService service;

    public DefaultApiProvider(@NonNull Context context, @NonNull String baseUrl) {
        this.service = createService(context, baseUrl);
    }

    private DataService createService(@NonNull Context context, @NonNull String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttp.getClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DataService.class);
    }

    @NonNull
    @Override
    public DataService dataService() {
        return service;
    }
}