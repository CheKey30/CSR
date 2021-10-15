package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Utils.URLGeneratorUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class RealTimeTrafficInfoJob {

    public static void main(String[] args) throws IOException, InterruptedException {
        HashMap<String,String> addresses = URLGeneratorUtil.getBoundCornerAddress();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse("2021-10-15 07:00:00",df);
        LocalDateTime endTime = LocalDateTime.parse("2021-10-15 10:00:00",df);
        while(true){
            if(LocalDateTime.now().isAfter(beginTime) && LocalDateTime.now().isBefore(endTime)){
                System.out.println("Sending Data "+ df.format(LocalDateTime.now()));
                for(String key: addresses.keySet()){
                    TrafficInfoThread trafficInfoThread = new TrafficInfoThread(key,addresses.get(key));
                    Thread thread = new Thread(trafficInfoThread);
                    thread.start();
                }
            }else {
                System.out.println("Data obtaining waiting "+ df.format(LocalDateTime.now()));
            }
            Thread.sleep(60000);
        }
    }
}



