package com.csr.datasearching.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csr.datasearching.model.Station;
import com.csr.datasearching.service.intf.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 10:52 上午
 */
@RestController
public class DataSearchingController{
    @Autowired
    ChargingStationService chargingStationService;

    @GetMapping("/index")
    public String index(){
        return "Welcome to CSR";
    }

    @GetMapping("/csr/station")
    public @ResponseBody JSONArray station(@RequestParam Station station){
        List<Station> list = chargingStationService.getStationInfo(station);
        JSONArray ret = new JSONArray();
        if(CollectionUtils.isEmpty(list) == false){
            list.stream().forEach(ele -> {
                JSONObject json = (JSONObject) JSONObject.toJSON(ele);
                ret.add(json);
            });
        }
        return ret;
    }
}
