package com.CSR.DataObtaining.Jobs;


import com.CSR.DataObtaining.KafkaStorage.Producer;
import com.CSR.DataObtaining.Utils.URLGeneratorUtil;

import java.io.IOException;

public class TrafficInfoThread implements Runnable{
    private String leftDown;
    private String rightUp;

    public TrafficInfoThread(String leftDown, String rightUp) {
        this.leftDown = leftDown;
        this.rightUp = rightUp;
    }

    @Override
    public void run() {
        String result = null;
        try {
            result = URLGeneratorUtil.getRealTimeBoundTrafficInfo(leftDown,rightUp);
            System.out.println(result);
            Producer producer = new Producer();
            producer.sendToKafka("trafficInfo",result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.sendToKafka("trafficInfo","{\"status\":0,\"message\":\"成功\",\"description\":\"北五环:自东向西较为畅通;自西向东畅通;东向西,佳全教会附近拥堵。\",\"evaluation\":{\"status\":1,\"status_desc\":\"自东向西较为畅通;自西向东畅通\"},\"road_traffic\":[{\"congestion_sections\":[{\"congestion_distance\":780,\"speed\":13.45,\"status\":3,\"congestion_trend\":\"加重\",\"section_desc\":\"东向西,佳全教会附近\"}],\"road_name\":\"北五环\"}]}");
    }
}
