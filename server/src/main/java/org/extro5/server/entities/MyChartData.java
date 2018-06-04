package org.extro5.server.entities;

import java.util.HashMap;
import java.util.List;

public class MyChartData {

    private HashMap<String, String> chartMap;

    private List<String> dataList;

    private List<ActivityNameDiagram> activityNameDiagram;

    public MyChartData(List<ActivityNameDiagram> activityNameDiagram) {
        this.activityNameDiagram = activityNameDiagram;
    }

    public MyChartData(HashMap<String, String> chartMap, List<String> dataList) {
        this.chartMap = chartMap;
        this.dataList = dataList;
    }

    public HashMap<String, String> getChartMap() {
        return chartMap;
    }

    public void setChartMap(HashMap<String, String> chartMap) {
        this.chartMap = chartMap;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public List<ActivityNameDiagram> getActivityNameDiagram() {
        return activityNameDiagram;
    }

    public void setActivityNameDiagram(List<ActivityNameDiagram> activityNameDiagram) {
        this.activityNameDiagram = activityNameDiagram;
    }
}