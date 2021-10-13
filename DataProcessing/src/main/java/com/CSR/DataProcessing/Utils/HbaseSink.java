package com.CSR.DataProcessing.Utils;


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

public class HbaseSink extends RichSinkFunction<TrafficInfo> implements Serializable {
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

    public void invoke(TrafficInfo datas, Context context) throws Exception {
        createTable("trafficInfo");
        // 写数据
        Put put = new Put(Bytes.toBytes(String.valueOf(System.currentTimeMillis())));
        put.addColumn(Bytes.toBytes("traffic"),Bytes.toBytes("info"),Bytes.toBytes(String.valueOf(datas)));
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
            // 固定只有 data 列簇
            hTableDescriptor.addFamily(new HColumnDescriptor("data"));
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

