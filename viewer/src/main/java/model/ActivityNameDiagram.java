package model;

public class ActivityNameDiagram {

    private String gender;

    private String activityName;

    public ActivityNameDiagram(String gender, String activityName) {
        this.gender = gender;
        this.activityName = activityName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}