package com.CSR.DataObtaining.Utils;

import com.CSR.DataObtaining.Beans.CongestionSection;
import com.CSR.DataObtaining.Beans.Evaluation;
import com.CSR.DataObtaining.Beans.RoadTraffic;
import com.CSR.DataObtaining.Beans.TrafficInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class TrafficInfoTranceUtil {

    public static TrafficInfo getTrafficInfoFromString(String json){
        TrafficInfo trafficInfo = new TrafficInfo();
//        JSONObject trafficJson = JSON.parseObject(json);
//        trafficInfo.setStatus(trafficJson.getInteger("status"));
//        trafficInfo.setMessage(trafficJson.getString("message"));
//        trafficInfo.setDescription(trafficJson.getString("description"));
//        Evaluation evaluation = new Evaluation();
//        JSONObject evaluationJson = JSON.parseObject(trafficJson.getString("evaluation"));
//        evaluation.setStatus(evaluationJson.getInteger("status"));
//        evaluation.setStatusDesc(evaluationJson.getString("status_desc"));
//        JSONArray roadTrafficJsonArray = trafficJson.getJSONArray("road_traffic");
//        List<RoadTraffic> roadTraffics = JSON.parseObject(roadTrafficJsonArray.toJSONString(), new TypeReference<List<RoadTraffic>>(){});
//        trafficInfo.setRoadTraffic(roadTraffics);
//        trafficInfo.setEvaluation(evaluation);
        trafficInfo = JSON.parseObject(json,TrafficInfo.class);
        return trafficInfo;
    }
}
