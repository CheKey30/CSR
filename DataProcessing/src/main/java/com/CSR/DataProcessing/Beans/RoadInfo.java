package com.CSR.DataProcessing.Beans;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 4:27 下午
 */
public class RoadInfo {
    private String roadName;
    private String roadIdentity;
    private String sectionDesc;
    private int status;
    private double speed;
    private int congestionDistance;
    private String congestionTrend;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadIdentity() {
        return roadIdentity;
    }

    public void setRoadIdentity(String roadIdentity) {
        this.roadIdentity = roadIdentity;
    }

    public String getSectionDesc() {
        return sectionDesc;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCongestionDistance() {
        return congestionDistance;
    }

    public void setCongestionDistance(int congestionDistance) {
        this.congestionDistance = congestionDistance;
    }

    public String getCongestionTrend() {
        return congestionTrend;
    }

    public void setCongestionTrend(String congestionTrend) {
        this.congestionTrend = congestionTrend;
    }
}
