package com.csr.datasearching.dao;

import com.csr.datasearching.mapper.StationMapper;
import com.csr.datasearching.model.Station;
import com.csr.datasearching.model.StationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 11:36 上午
 */

@Repository
public class StationInfoDao {

    @Autowired
    StationMapper stationMapper;


    public List<Station> selectByExample(StationExample example){
        return stationMapper.selectByExample(example);
    }


}
