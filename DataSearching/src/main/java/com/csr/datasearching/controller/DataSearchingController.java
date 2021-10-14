package com.csr.datasearching.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:shuchen
 * @date: 2021/10/14
 * @time: 10:52 上午
 */
@RestController
public class DataSearchingController {

    @GetMapping("/index")
    public String index(){
        return "Welcome to CSR";
    }

    @GetMapping("/csr")
    public String station(@RequestParam String cid){
        return "cid: "+cid +" "+ "station: street a";
    }
}
