package com.example.datacollector.api;

import android.support.annotation.NonNull;

public interface ApiProvider {

    @NonNull
    DataService dataService();
}
