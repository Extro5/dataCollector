package org.extro5.server.entities;

public class BaseRequestObject {

    private String apiKey;

    private User user;

    public BaseRequestObject(String apiKey, User user) {
        this.apiKey = apiKey;
        this.user = user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
