package com.csr.datasearching.service.impl;

import com.CSR.DataObtaining.Beans.ChargingStationInfo;
import com.CSR.DataProcessing.Beans.RoadTraffic;
import com.csr.datasearching.dao.CarInfoDao;
import com.csr.datasearching.dao.RoadInfoDao;
import com.csr.datasearching.dao.StationInfoDao;
import com.csr.datasearching.model.Station;
import com.csr.datasearching.model.StationExample;
import com.csr.datasearching.service.intf.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 11:08 上午
 */
public class ChargingStationServiceImpl implements ChargingStationService {

    @Autowired
    StationInfoDao stationInfoDao;

    @Autowired
    RoadInfoDao roadInfoDao;

    @Autowired
    CarInfoDao carInfoDao;

    public String getStationNameByCarId(String carId) {
        return null;
    }

    public List<Station> getStationInfo(Station info) {
        StationExample example = new StationExample();
        StationExample.Criteria criteria = example.createCriteria();
        if(info.getName()!=null){
            criteria.andNameLike(info.getName());
        }
        if(info.getAddress()!=null){
            criteria.andAddressLike(info.getAddress());
        }
        if(info.getArea()!=null){
            criteria.andAreaLike(info.getArea());
        }
        if(info.getLng()!=null){
            criteria.andLatEqualTo(Float.valueOf(info.getLng()));
        }
        if(info.getLat()!=null){
            criteria.andLatEqualTo(Float.valueOf(info.getLat()));
        }
        List<Station> res = stationInfoDao.selectByExample(example);
        return res;
    }

    public String getRoadInfo(RoadTraffic info) {
        return null;
    }

    public String getCongestionByRoadName(String roadName){return null;}



}
