package com.example.datacollector.api;

import android.support.annotation.NonNull;

import com.example.datacollector.model.request_object.AboutUserInfo;
import com.example.datacollector.model.request_object.BaseRequestObject;
import com.example.datacollector.model.request_object.DataException;
import com.example.datacollector.model.request_object.DeviceInfo;
import com.example.datacollector.model.request_object.SystemInformation;
import com.example.datacollector.model.response_object.IdData;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface DataService {

    @POST("/add_user")
    Observable<IdData> sendUser(@NonNull @Body BaseRequestObject baseRequestObject);

    @POST("/exception")
    Observable<String> sendException(@NonNull @Body DataException exception);

    @POST("/system")
    Observable<String> sendSystemInfo(@NonNull @Body SystemInformation systemInformation);

    @POST("/device_info")
    Observable<String> sendDeviceInfo(@NonNull @Body DeviceInfo deviceInfo);

    @POST("/selection_entity")
    Observable<IdData> sendAboutUserInfo(@NonNull @Body AboutUserInfo aboutUserInfo);
}
