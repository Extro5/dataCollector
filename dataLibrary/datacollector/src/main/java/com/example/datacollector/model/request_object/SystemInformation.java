package com.example.datacollector.model.request_object;

import com.google.gson.annotations.SerializedName;

public class SystemInformation {

    @SerializedName("commandLine")
    private String commandLine;

    @SerializedName("elapsedTime")
    private String elapsedTime;

    @SerializedName("activityName")
    private String activityName;

    @SerializedName("userId")
    private String userId;

    public SystemInformation(String commandLine, String elapsedTime, String userId, String activityName) {
        this.commandLine = commandLine;
        this.elapsedTime = elapsedTime;
        this.userId = userId;
        this.activityName = activityName;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
