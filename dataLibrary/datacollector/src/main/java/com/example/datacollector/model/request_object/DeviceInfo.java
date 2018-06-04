package com.example.datacollector.model.request_object;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {

    @SerializedName("manufacturer")
    private String manufacturer;

    @SerializedName("marketName")
    private String marketName;

    @SerializedName("model")
    private String model;

    @SerializedName("userId")
    private String userId;

    public DeviceInfo(String manufacturer, String marketName, String model, String userId) {
        this.manufacturer = manufacturer;
        this.marketName = marketName;
        this.model = model;
        this.userId = userId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}