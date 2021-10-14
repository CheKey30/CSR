package com.csr.datasearching.service.intf;


import com.CSR.DataObtaining.Beans.ChargingStationInfo;
import com.CSR.DataProcessing.Beans.RoadTraffic;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 11:08 上午
 */
public interface ChargingStationService {

    public String getStationNameByCarId(String carId);

    public String getStationInfo(ChargingStationInfo info);

    public String getRoadInfo(RoadTraffic info);
}
