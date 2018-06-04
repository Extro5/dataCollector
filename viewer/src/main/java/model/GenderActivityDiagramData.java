package model;

import java.util.List;

public class GenderActivityDiagramData {

    private List<String> activityName;

    private List<String> countActivity;

    public GenderActivityDiagramData(List<String> activityName, List<String> countActivity) {
        this.activityName = activityName;
        this.countActivity = countActivity;
    }

    public List<String> getActivityName() {
        return activityName;
    }

    public void setActivityName(List<String> activityName) {
        this.activityName = activityName;
    }

    public List<String> getCountActivity() {
        return countActivity;
    }

    public void setCountActivity(List<String> countActivity) {
        this.countActivity = countActivity;
    }
}
