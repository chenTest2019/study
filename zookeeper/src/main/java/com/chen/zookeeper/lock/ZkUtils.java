package com.chen.zookeeper.lock;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZkUtils {

    static ZooKeeper getZk(Lock.SessionWatch sessionWatch) throws IOException {
        //ZooKeeperServer zooKeeperServer=new ZooKeeperServer();
        String host = "192.168.80.137:2181,192.168.80.138:2181,192.168.80.139:2181,192.168.80.140:2181/testLock";
        //host="192.168.80.138:2181/testLock";
        //这个时间设置过短 会导致连不上 虚拟机网络原因？？
        ZooKeeper zooKeeper = new ZooKeeper(host, 30000, sessionWatch);
        //ZooKeeper.WatchRegistration
        //ZooKeeper.States state = zooKeeper.getState();

        return zooKeeper;
    }
}

