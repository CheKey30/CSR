package com.CSR.DataProcessing.Utils;


import com.CSR.DataProcessing.Beans.RoadInfo;
import com.CSR.DataProcessing.Beans.TrafficInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class HbaseSink extends RichSinkFunction<RoadInfo> implements Serializable {
    private Logger log;

    private String hbase_zookeeper_host;
    private String hbase_zookeeper_port;

    private Connection connection;
    private Admin admin;

    public HbaseSink(String hbase_zookeeper_host, String hbase_zookeeper_port) {
        this.hbase_zookeeper_host = hbase_zookeeper_host;
        this.hbase_zookeeper_port = hbase_zookeeper_port;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        log = Logger.getLogger(HbaseSink.class);

        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", hbase_zookeeper_port);
        configuration.set("hbase.zookeeper.quorum", hbase_zookeeper_host);

        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
    }

    public void invoke(RoadInfo data, Context context) throws Exception {
        createTable("trafficInfo");
        //rowkey
        Put put = new Put(Bytes.toBytes(data.getRoadIdentity()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("roadName"),Bytes.toBytes(data.getRoadName()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("congestionTrend"),Bytes.toBytes(data.getCongestionTrend()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("sectionDesc"),Bytes.toBytes(data.getSectionDesc()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("status"),Bytes.toBytes(data.getStatus()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("speed"),Bytes.toBytes(data.getSpeed()));
        put.addColumn(Bytes.toBytes("roadData"),Bytes.toBytes("congestionDistance"),Bytes.toBytes(data.getCongestionDistance()));
        connection.getTable(TableName.valueOf("trafficInfo")).put(put);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    /**
     * 创建 hbase 表
     */
    private void createTable(String tableName) throws Exception {
        createNamespace(tableName);
        TableName table = TableName.valueOf(tableName);
        if (! admin.tableExists(table)) {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(table);
            hTableDescriptor.addFamily(new HColumnDescriptor("roadData"));
            admin.createTable(hTableDescriptor);
        }
    }

    /**
     * 创建命名空间
     */
    private void createNamespace(String namespace) throws Exception {
        try {
            admin.getNamespaceDescriptor(namespace);
        } catch (NamespaceNotFoundException e) {
            admin.createNamespace(NamespaceDescriptor.create(namespace).build());
        }
    }
}

