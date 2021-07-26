package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Utils.URLGeneratorUtil;

import java.io.IOException;
import java.util.HashMap;

public class RealTimeTrafficInfoJob {

    public static void main(String[] args) throws IOException, InterruptedException {
        HashMap<String,String> addresses = URLGeneratorUtil.getBoundCornerAddress();
        while(true){
            for(String key: addresses.keySet()){
                TrafficInfoThread trafficInfoThread = new TrafficInfoThread(key,addresses.get(key));
                Thread thread = new Thread(trafficInfoThread);
                thread.start();
            }
            Thread.sleep(60000);
        }

    }

}



