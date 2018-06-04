package org.extro5.server.entities;

public class DeviceInfo {

    private String manufacturer;

    private String marketName;

    private String model;

    private String userId;

    public DeviceInfo() {
    }

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