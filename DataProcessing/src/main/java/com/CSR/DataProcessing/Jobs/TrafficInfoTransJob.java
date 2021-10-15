package com.CSR.DataProcessing.Jobs;

import akka.stream.impl.io.InputStreamSinkStage;
import com.CSR.DataProcessing.Beans.CongestionSection;
import com.CSR.DataProcessing.Beans.RoadInfo;
import com.CSR.DataProcessing.Beans.RoadTraffic;
import com.CSR.DataProcessing.Beans.TrafficInfo;
import com.CSR.DataProcessing.Utils.HbaseSink;
import com.CSR.DataProcessing.Utils.TrafficInfoTranceUtil;
import com.google.gson.JsonArray;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TrafficInfoTransJob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","kafka129:9092;zookeeper129:9092;flink132:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer011<String>("trafficInfo", new SimpleStringSchema(),properties));

        DataStream<RoadTraffic> roadTrafficDataStream = dataStream.flatMap(new FlatMapFunction<String, RoadTraffic>() {
            @Override
            public void flatMap(String s, Collector<RoadTraffic> collector) throws Exception {
                TrafficInfo  trafficInfo = TrafficInfoTranceUtil.getTrafficInfoFromString(s);
                if(trafficInfo!=null){
                    for(RoadTraffic r: trafficInfo.getRoadTraffic()){
                        if(r.getCongestionSections()!=null){
                            System.out.println(r.getRoadName());
                            collector.collect(r);
                        }
                    }
                }
            }
        });

        DataStream<RoadInfo> roadInfoDataStream = roadTrafficDataStream.flatMap(new FlatMapFunction<RoadTraffic, RoadInfo>() {
            @Override
            public void flatMap(RoadTraffic roadTraffic, Collector<RoadInfo> collector) throws Exception {
                for(CongestionSection c : roadTraffic.getCongestionSections()){
                    RoadInfo info = new RoadInfo();
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    // 反复调用update输入数据:
                    md.update(roadTraffic.getRoadName().getBytes("UTF-8"));
                    md.update(c.getSectionDesc().getBytes("UTF-8"));
                    byte[] result = md.digest();
                    info.setRoadName(roadTraffic.getRoadName());
                    info.setRoadIdentity(new BigInteger(1, result).toString(16));
                    info.setCongestionTrend(c.getCongestionTrend());
                    info.setSectionDesc(c.getSectionDesc());
                    info.setSpeed(c.getSpeed());
                    info.setStatus(c.getStatus());
                    info.setCongestionDistance(c.getCongestionDistance());
                    collector.collect(info);
                }
            }
        });

        roadInfoDataStream.addSink(new HbaseSink("zookeeper129","2181"));

        env.execute("TRAFFIC_INFO");
    }
}
