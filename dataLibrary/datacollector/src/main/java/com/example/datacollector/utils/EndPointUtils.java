package com.example.datacollector.utils;

import android.support.annotation.NonNull;

import com.example.datacollector.BuildConfig;

public class EndPointUtils {

    @NonNull
    public static String getBaseUrl() {
        return BuildConfig.DEBUG ? BuildConfig.BASE_URL_DEBUG : BuildConfig.BASE_URL_PRODUCTION;
    }
}