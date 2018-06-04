package com.example.datacollector.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.datacollector.model.request_object.DeviceInfo;
import com.example.datacollector.rest.DataSender;
import com.jaredrummler.android.device.DeviceName;

import me.tatarka.rxloader.RxLoaderManager;

public class AboutDeviceUtils {

    public static void sendDeviceInfo(final Activity activity) {

        DeviceName.with(activity).request(new DeviceName.Callback() {
            @Override
            public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                String manufacturer = info.manufacturer;
                String name = info.marketName;
                String model = info.model;
                DeviceInfo deviceInfo = new DeviceInfo(manufacturer, name, model, PreferencesUtils.getUserId(activity));
                DataSender dataSender = new DataSender(RxLoaderManager.get(activity), activity.getApplication());
                dataSender.deviceInfoSender(deviceInfo);
            }
        });
    }
}