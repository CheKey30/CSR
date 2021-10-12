package com.CSR.DataProcessing.Utils;

import com.CSR.DataProcessing.Beans.TrafficInfo;
import com.alibaba.fastjson.JSON;

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
