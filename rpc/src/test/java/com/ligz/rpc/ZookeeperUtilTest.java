package com.ligz.rpc;

import com.ligz.rpc.util.ZookeeperUtils;

import static com.ligz.rpc.util.ZookeeperUtils.ZK_REGISTER_ROOT_PATH;

public class ZookeeperUtilTest {
    public static void main(String[] args) {
        ZookeeperUtils.clearRegistry(
                ZK_REGISTER_ROOT_PATH + "/" + "com.ligz.rpc.demo.EchoService" + "/" + "127.0.0.1:8000");
    }
}
