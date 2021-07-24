package com.CSR.DataObtaining.Beans;

public class CongestionSection {
    private String sectionDesc;
    private int status;
    private double speed;
    private int congestionDistance;
    private String congestionTrend;

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
