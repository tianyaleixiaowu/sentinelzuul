package com.example.sentinelzuul.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author wuweifeng wrote on 2019/7/1.
 */
public class ZookeeperConfigSender {
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;

    public static void main(String[] args) throws Exception {
        final String remoteAddress = "localhost:2181";
        final String rule = "[\n"
                + "  {\n"
                + "    \"resource\": \"baobao_api\",\n"
                + "    \"resourceMode\": 1,\n"
                + "    \"controlBehavior\": 0,\n"
                + "    \"count\": 1.0,\n"
                + "    \"grade\": 1,\n"
                + "  }\n"
                + "]";

        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(remoteAddress, new ExponentialBackoffRetry
                (SLEEP_TIME, RETRY_TIMES));
        zkClient.start();
        String path = "/sentinel_zuul_rule_config/zuulConfig";
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, rule.getBytes());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zkClient.close();
    }
}
