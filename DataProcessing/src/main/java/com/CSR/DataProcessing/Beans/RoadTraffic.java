package com.CSR.DataProcessing.Beans;

import java.util.ArrayList;

public class RoadTraffic {
    private String roadName;
    private ArrayList<CongestionSection> congestionSections;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public ArrayList<CongestionSection> getCongestionSections() {
        return congestionSections;
    }

    public void setCongestionSections(ArrayList<CongestionSection> congestionSections) {
        this.congestionSections = congestionSections;
    }
}
