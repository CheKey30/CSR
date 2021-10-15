package com.csr.datasearching.service.intf;


import com.CSR.DataObtaining.Beans.ChargingStationInfo;
import com.CSR.DataProcessing.Beans.RoadTraffic;
import com.csr.datasearching.model.Station;

import java.util.List;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 11:08 上午
 */
public interface ChargingStationService {

    public String getStationNameByCarId(String carId);

    public List<Station> getStationInfo(Station info);

    public String getRoadInfo(RoadTraffic info);
}
