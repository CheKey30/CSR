package com.CSR.DataProcessing.Jobs;

import com.CSR.DataProcessing.Beans.TrafficInfo;
import com.CSR.DataProcessing.Utils.HbaseSink;
import com.CSR.DataProcessing.Utils.TrafficInfoTranceUtil;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
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

        DataStream<TrafficInfo> trafficInfoDataStrem = dataStream.map(new MapFunction<String, TrafficInfo>() {
            @Override
            public TrafficInfo map(String s) throws Exception {
                return TrafficInfoTranceUtil.getTrafficInfoFromString(s);

            }
        });

        trafficInfoDataStrem.addSink(new HbaseSink("zookeeper129","2181"));

        env.execute("TRAFFIC_INFO");
    }

    public static void write2Hbase(String value) throws IOException {
        TableName tableName = TableName.valueOf("trafficInfo");
        String cf = "ke";
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "kafka129:9092;zookeeper129:9092;flink132:9092");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.setInt("hbase.rpc.timeout", 30000);
        config.setInt("hbase.client.operation.timeout", 30000);
        config.setInt("hbase.client.scanner.timeout.period", 30000);
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();
        if (!admin.tableExists(tableName)){
            admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(cf)));
        }
        Table table = connection.getTable(tableName);
        TimeStamp ts = new TimeStamp(new Date());
        Date date = ts.getDate();
        Put put = new Put(Bytes.toBytes(date.getTime()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("test"), Bytes.toBytes(value));
        System.out.println("写入:" + value);
        table.put(put);
        table.close();
        connection.close();
    }
}
