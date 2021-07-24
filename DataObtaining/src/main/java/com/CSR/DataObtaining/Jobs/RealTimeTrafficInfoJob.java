package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Beans.TrafficInfo;
import com.CSR.DataObtaining.Utils.HttpClientUtil;
import com.CSR.DataObtaining.Utils.TrafficInfoTranceUtil;

import java.io.IOException;
import java.util.HashMap;

public class RealTimeTrafficInfoJob {
    public static void main(String[] args) throws IOException, InterruptedException {
        // get parameters from txt file
        HashMap<String,String> params = HttpClientUtil.readParametersFromFile("E:\\CSR\\DataObtaining\\src\\main\\resources\\TrafficParams.txt");
        String url = "http://api.map.baidu.com/traffic/v1/road";
        String finalUrl = HttpClientUtil.addParameters(params,url);
//        System.out.println(finalUrl);
        while(true){
            Thread.sleep(60000);
            String result = HttpClientUtil.doGet(finalUrl,"UTF-8");
            System.out.println(result);
            TrafficInfo trafficInfo = TrafficInfoTranceUtil.getTrafficInfoFromString(result);

            // todo put traffic into kafka
        }



    }

}



