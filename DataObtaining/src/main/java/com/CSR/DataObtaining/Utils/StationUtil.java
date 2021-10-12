package com.CSR.DataObtaining.Utils;

import com.CSR.DataObtaining.Beans.ChargingStationInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StationUtil {
    public static List<ChargingStationInfo> getStationsFromString(String json){
        List<ChargingStationInfo> res = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray stations = jsonObject.getJSONArray("results");
        for(int i=0;i<stations.size();i++){
            JSONObject object = stations.getJSONObject(i);
            ChargingStationInfo info = new ChargingStationInfo();
            info.setName(object.getString("name"));
            info.setLat(object.getJSONObject("location").getString("lat"));
            info.setLng(object.getJSONObject("location").getString("lng"));
            info.setAddress(object.getString("address"));
            info.setProvince(object.getString("province"));
            info.setCity(object.getString("city"));
            info.setArea(object.getString("area"));
            info.setStreet_id(object.getString("street_id"));
            info.setDetail(object.getString("detail"));
            info.setUid(object.getString("uid"));
            res.add(info);
        }
        return res;
    }

    public static int getTotalNum(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        int res = jsonObject.getInteger("total");
        return res;
    }


    public static void insertIntoDB(List<ChargingStationInfo> stationInfos){
            String sql="insert into charging_station.charging_stations (name, " +
                    "lat," +
                    "lng," +
                    "address," +
                    "province," +
                    "city," +
                    "area," +
                    "street_id," +
                    "detail," +
                    "uid) values";
            StringBuffer sb = new StringBuffer();
            sb.append(sql);
            for(ChargingStationInfo chargingStationInfo: stationInfos) {
                sb.append("(");
                sb.append("\"").append(chargingStationInfo.getName()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getLat()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getLng()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getAddress()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getProvince()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getCity()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getArea()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getStreet_id()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getDetail()).append("\"");
                sb.append(",");
                sb.append("\"").append(chargingStationInfo.getUid()).append("\"");
                sb.append(")");
                sb.append(",");
            }

            String finalSql = sb.toString();
            finalSql = finalSql.substring(0,finalSql.length()-1);
            executeSql(finalSql);
    }


    public static void cleanTable(){
        String sql = "delete from charging_station.charging_stations";
        executeSql(sql);
    }


    public static void executeSql(String sql){
        Connection connection=null;
        PreparedStatement ps=null;
        try {
            //1.register
            Class.forName("com.mysql.jdbc.Driver");

            //2.getConnection
            String url="jdbc:mysql://collector131:3306";
            Properties info=new Properties();
            info.put("user", "root");
            info.put("password", "QQ123456!");
            connection= DriverManager.getConnection(url, info);

            ps = connection.prepareStatement(sql);

            //4.excuteUpdate
            int resultSet=ps.executeUpdate();
            if(resultSet>0){
                //如果插入成功，则打印success
                System.out.println("Sucess");
            }else{
                //如果插入失败，则打印Failure
                System.out.println("Failure");
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //5.关闭资源
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
