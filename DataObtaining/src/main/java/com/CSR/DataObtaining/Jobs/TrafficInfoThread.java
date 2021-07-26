package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Beans.TrafficInfo;
import com.CSR.DataObtaining.KafkaStorage.Producer;
import com.CSR.DataObtaining.Utils.TrafficInfoTranceUtil;
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
            TrafficInfo trafficInfo = TrafficInfoTranceUtil.getTrafficInfoFromString(result);
            System.out.println(result);
            Producer producer = new Producer();
            producer.sendToKafka("trafficInfo",result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
