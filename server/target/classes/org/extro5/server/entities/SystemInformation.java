package org.extro5.server.entities;

public class SystemInformation {

    private String commandLine;

    private String elapsedTime;

    private String userId;

    private String activityName;

    public SystemInformation(){

    }

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