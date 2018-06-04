package com.example.datacollector.model.request_object;

public class AboutUserInfo {

    private String apiKey;

    private String aboutUserInfo;

    public AboutUserInfo(String apiKey, String aboutUserInfo) {
        this.apiKey = apiKey;
        this.aboutUserInfo = aboutUserInfo;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAboutUserInfo() {
        return aboutUserInfo;
    }

    public void setAboutUserInfo(String aboutUserInfo) {
        this.aboutUserInfo = aboutUserInfo;
    }
}
