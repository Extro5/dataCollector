package com.example.datacollector.rest;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.datacollector.BuildConfig;
import com.example.datacollector.api.ApiFactory;
import com.example.datacollector.api.ApiProvider;
import com.example.datacollector.api.DefaultApiProvider;
import com.example.datacollector.model.request_object.AboutUserInfo;
import com.example.datacollector.model.request_object.BaseRequestObject;
import com.example.datacollector.model.request_object.DeviceInfo;
import com.example.datacollector.model.request_object.SystemInformation;
import com.example.datacollector.model.request_object.DataException;
import com.example.datacollector.model.request_object.User;
import com.example.datacollector.model.response_object.IdData;
import com.example.datacollector.utils.EndPointUtils;
import com.example.datacollector.utils.PreferencesUtils;

import java.util.Arrays;

import me.tatarka.rxloader.RxLoader;
import me.tatarka.rxloader.RxLoaderManager;
import me.tatarka.rxloader.RxLoaderObserver;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataSender {

    private Context context;

    public DataSender(@NonNull RxLoaderManager rxLoaderManager, @NonNull Application application) {
        this.rxLoaderManager = rxLoaderManager;

        ApiProvider apiProvider = new DefaultApiProvider(application, EndPointUtils.getBaseUrl());
        ApiFactory.setProvider(apiProvider);
        context = application;
    }

    private RxLoaderManager rxLoaderManager;

    private RxLoader<IdData> userLoader;

    private RxLoader<String> exceptionLoader;

    private RxLoader<String> systemInformationLoader;

    private RxLoader<String> deviceInfoLoader;

    private RxLoader<IdData> aboutUserInfoLoader;

    private static final String LOADER_USER_SENDER = "loader_user_sender";

    private static final String LOADER_EXCEPTION_SENDER = "loader_exception_sender";

    private static final String LOADER_SYSTEM_INFORMATION = "loader_system_information";

    private static final String LOADER_DEVICE_INFORMATION = "loader_device_information";

    private static final String LOADER_ABOUT_USER_INFORMATION = "loader_about_user_information";

    public void userSender(@NonNull String firstName, @NonNull String lastName, @NonNull String fatherName, @NonNull String email, @NonNull String gender, @NonNull String phone, @NonNull String age, @NonNull String hobby, @NonNull String profession) {

        User user = new User(firstName, lastName, fatherName, email, gender, phone, age, hobby, profession);
        BaseRequestObject baseRequestObject = new BaseRequestObject(BuildConfig.API_KEY, user);
        userLoader = rxLoaderManager.create(LOADER_USER_SENDER,
                ApiFactory
                        .getProvider()
                        .dataService()
                        .sendUser(baseRequestObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<IdData>() {

                    @Override
                    public void onNext(IdData value) {
                        if (value != null) {
                            Log.d("MyKeyPref", value.getIdUser());
                            PreferencesUtils.saveUserId(context, value.getIdUser());
                        }
                        userLoader.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLoader.clear();
                    }
                }).restart();
    }

    public void exceptionSender(@NonNull DataException exception) {

        exceptionLoader = rxLoaderManager.create(LOADER_EXCEPTION_SENDER,
                ApiFactory
                        .getProvider()
                        .dataService()
                        .sendException(exception)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<String>() {

                    @Override
                    public void onNext(String value) {
                        exceptionLoader.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        exceptionLoader.clear();
                    }
                }).restart();
    }

    public void systemInformationSender(String commandLine, long elapsedTime) {
        SystemInformation systemInformation = new SystemInformation(commandLine, String.valueOf(elapsedTime), PreferencesUtils.getUserId(context), PreferencesUtils.getActivityName(context));
        systemInformationLoader = rxLoaderManager.create(LOADER_SYSTEM_INFORMATION,
                ApiFactory
                        .getProvider()
                        .dataService()
                        .sendSystemInfo(systemInformation)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<String>() {

                    @Override
                    public void onNext(String value) {
                        if (value.equals("success")) {
                            Log.d("MyKeyOnpauseNext", "next");

                        }

                        systemInformationLoader.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MyKeyOnpause1", e.getMessage());
                        Log.d("MyKeyOnpause1", e.getLocalizedMessage());
                        Log.d("MyKeyOnpause1", Arrays.toString(e.getStackTrace()));
                        systemInformationLoader.clear();
                    }
                }).restart();

    }

    public void deviceInfoSender(@NonNull DeviceInfo deviceInfo) {
        deviceInfoLoader = rxLoaderManager.create(LOADER_DEVICE_INFORMATION,
                ApiFactory
                        .getProvider()
                        .dataService()
                        .sendDeviceInfo(deviceInfo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<String>() {

                    @Override
                    public void onNext(String value) {
                        deviceInfoLoader.clear();
                    }

                    @Override
                    public void onError(Throwable e) {

                        deviceInfoLoader.clear();
                    }
                }).restart();
    }

    public void aboutUserInformationSender(@NonNull String aboutUserValue) {
        AboutUserInfo aboutUserInfo = new AboutUserInfo(BuildConfig.API_KEY, aboutUserValue);
        aboutUserInfoLoader = rxLoaderManager.create(LOADER_ABOUT_USER_INFORMATION,
                ApiFactory
                        .getProvider()
                        .dataService()
                        .sendAboutUserInfo(aboutUserInfo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                , new RxLoaderObserver<IdData>() {

                    @Override
                    public void onNext(IdData value) {
                        if (value != null) {
                            Log.d("MyKeyPref", value.getIdUser());
                            PreferencesUtils.saveUserId(context, value.getIdUser());
                        }
                        aboutUserInfoLoader.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        aboutUserInfoLoader.clear();
                    }
                }).restart();
    }
}