package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Beans.TrafficInfo;
import com.CSR.DataObtaining.Utils.HttpClientUtil;
import com.CSR.DataObtaining.Utils.TrafficInfoTranceUtil;
import com.CSR.DataObtaining.Utils.URLGeneratorUtil;

import java.io.IOException;
import java.util.HashMap;

public class RealTimeTrafficInfoJob {
    public static void main(String[] args) throws IOException, InterruptedException {
        String leftDownAddress = "广东省深圳市南山区文心五路33号";
        String rightUpAddress = "深圳市南山区海斯路与海天一路交叉路口西侧(高新区联合总部大厦西南侧约50米)";
        while(true){
            String result = URLGeneratorUtil.getRealTimeBoundTrafficInfo(leftDownAddress,rightUpAddress);
            TrafficInfo trafficInfo = TrafficInfoTranceUtil.getTrafficInfoFromString(result);
            System.out.println(result);
            Thread.sleep(60000);
            // todo put traffic into kafka
        }



    }

}



