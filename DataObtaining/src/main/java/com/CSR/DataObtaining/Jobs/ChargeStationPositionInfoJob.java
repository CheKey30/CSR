package com.CSR.DataObtaining.Jobs;

import com.CSR.DataObtaining.Beans.ChargingStationInfo;
import com.CSR.DataObtaining.Utils.StationUtil;
import com.CSR.DataObtaining.Utils.URLGeneratorUtil;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.util.List;

public class ChargeStationPositionInfoJob {
    public static void main(String[] args) {
        try {
            String result = URLGeneratorUtil.getChargingStations(0);
            int total = StationUtil.getTotalNum(result);
            int round = (total/20);
            StationUtil.cleanTable();
            for(int i=0;i<=round;i++){
                System.out.println("current: "+i+" total: "+round);
                Thread.sleep(1000);
                result = URLGeneratorUtil.getChargingStations(i);
                List<ChargingStationInfo> infos = StationUtil.getStationsFromString(result);
                StationUtil.insertIntoDB(infos);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
