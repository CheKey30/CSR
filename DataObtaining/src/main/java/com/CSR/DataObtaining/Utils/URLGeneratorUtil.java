package com.CSR.DataObtaining.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLGeneratorUtil {

    private static final String DIR = "E:\\CSR\\DataObtaining\\src\\main\\resources\\TrafficInfoParams.txt";
    private static final String ADDRESSES_DIR = "E:\\CSR\\DataObtaining\\src\\main\\resources\\ArearAddress.txt";
    private static final String REAL_TIME_TRAFFIC = "http://api.map.baidu.com/traffic/v1/bound";
    private static final String ADDRESS_TO_COORDINATE = "http://api.map.baidu.com/geocoding/v3/";
    public static String addParameters(HashMap<String,String> params, String url) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append("?");
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        for(String key: params.keySet()){
            sb.append(key);
            sb.append("=");
            Matcher m = p.matcher(params.get(key));
            if(m.find()){
                sb.append(URLEncoder.encode(params.get(key),"utf-8"));
            }else {
                sb.append(params.get(key));
            }
            sb.append("&");
        }
        String res = sb.toString();
        res = res.substring(0,res.length()-1);
        System.out.println("finalURL: "+ res);
        return res;
    }

    public static HashMap<String,String> readParametersFromFile(String dir, String split) throws IOException {
        HashMap<String,String> map = new HashMap<>();
        try{
            File file = new File(dir);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line!=null){
                map.put(line.split(split)[0],line.split(split)[1]);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
    public static String[] getCoordinateFromAddress(String address) throws IOException {
        HashMap<String,String> params = readParametersFromFile(DIR,":");
        params.put("address",address);
        params.put("output","json");
        String finalUrl = addParameters(params,ADDRESS_TO_COORDINATE);
        String result = HttpClientUtil.doGet(finalUrl,"UTF-8");
        String[] res = new String[2];
        JSONObject jsonObject = JSON.parseObject(result);
        res[0] = jsonObject.getJSONObject("result").getJSONObject("location").getString("lat");
        res[1] = jsonObject.getJSONObject("result").getJSONObject("location").getString("lng");
        return res;
    }

    public static String getRealTimeBoundTrafficInfo(String leftDownAddress, String rightUpAddress) throws IOException {
        String[] leftCoordinate = getCoordinateFromAddress(leftDownAddress);
        String[] rightCoordinate = getCoordinateFromAddress(rightUpAddress);
        HashMap<String,String> params = readParametersFromFile(DIR,":");
        params.put("bounds",leftCoordinate[0]+","+leftCoordinate[1]+";"+rightCoordinate[0]+","+rightCoordinate[1]);
        String finalUrl = addParameters(params,REAL_TIME_TRAFFIC);
        String result = HttpClientUtil.doGet(finalUrl,"UTF-8");
        return result;
    }

    public static HashMap<String,String> getBoundCornerAddress() throws IOException {
        return readParametersFromFile(ADDRESSES_DIR,";");
    }

    public static void main(String[] args) throws IOException {
        String res  = getRealTimeBoundTrafficInfo("深圳市南山区南光路46号","后海大道和滨海大道交叉口东北侧");
        System.out.println(res);
    }

}
