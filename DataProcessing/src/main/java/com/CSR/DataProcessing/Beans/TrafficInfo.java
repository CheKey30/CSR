package com.CSR.DataProcessing.Beans;

import java.util.List;

public class TrafficInfo {
    private int status;
    private String message;
    private String description;
    private Evaluation evaluation;
    private List<RoadTraffic> roadTraffic;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public List<RoadTraffic> getRoadTraffic() {
        return roadTraffic;
    }

    public void setRoadTraffic(List<RoadTraffic> roadTraffic) {
        this.roadTraffic = roadTraffic;
    }
}
