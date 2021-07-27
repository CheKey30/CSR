package com.CSR.DataProcessing.Jobs;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TrafficInfoTransJob {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer011<String>("trafficInfo", new SimpleStringSchema(),properties));
        String filePath = "E:\\CSR\\DataProcessing\\src\\main\\resources\\OutPutData.txt";
        DataStream<String> dataStreamTimed = dataStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = df.format(new Date());
                return date+"   "+ s;
            }
        });
        dataStreamTimed.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE).setParallelism(1);
        env.execute("TRAFFIC_INFO");
    }
}
