package com.ligz.rpc.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ZookeeperUtils {

    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    public static final String ZK_REGISTER_ROOT_PATH = "/my-rpc";
    private static final Map<String, List<byte[]>> SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();
    private static final Set<String> REGISTERED_PATH_SET = ConcurrentHashMap.newKeySet();
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    private static CuratorFramework zkClient = initZkClient();

    private ZookeeperUtils() {
    }

    private static CuratorFramework initZkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(DEFAULT_ZOOKEEPER_ADDRESS)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        try {
            if (!zkClient.blockUntilConnected(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Time out waiting to connect to ZK!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zkClient;
    }

    public static void createPersistentNode(String path, byte[] data) {
        try {
            if (REGISTERED_PATH_SET.contains(path) || zkClient.checkExists().forPath(path) != null) {
                log.info("The node already exists. The node is:[{}]", path);
            } else {
                //eg: /my-rpc/com.github.EchoService/127.0.0.1:8000
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data);
                log.info("The node was created successfully. The node is:[{}]", path);
            }
            REGISTERED_PATH_SET.add(path);
        } catch (Exception e) {
            log.error("create persistent node for path [{}] fail, error:{}", path, e);
        }
    }

    public static List<byte[]> getChildrenNodes(String parentPath) {
        if (SERVICE_ADDRESS_MAP.containsKey(parentPath)) {
            return SERVICE_ADDRESS_MAP.get(parentPath);
        }
        List<byte[]> data = null;
        try {
            List<String> childrenPath = zkClient.getChildren().forPath(parentPath);
            data = childrenPath.stream()
                    .map(path -> parentPath + "/" + path)
                    .map(ZookeeperUtils::getData)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            SERVICE_ADDRESS_MAP.put(parentPath, data);
            registerWatcher(parentPath);
        } catch (Exception e) {
            log.error("get children nodes for path [{}] fail", parentPath);
        }
        return data;
    }

    public static void clearRegistry(String path) {
        REGISTERED_PATH_SET.forEach(p -> {
            try {
                if (p.endsWith(path)) {
                    zkClient.delete().forPath(p);
                }
            } catch (Exception e) {
                log.error("clear registry for path [{}] fail", p);
            }
        });
        log.info("All registered services on the server are cleared:[{}]", REGISTERED_PATH_SET);
    }

    private static void registerWatcher(String parentPath) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, parentPath, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> childrenPath = curatorFramework.getChildren().forPath(parentPath);
            List<byte[]> data = childrenPath.stream()
                    .map(path -> parentPath + "/" + path)
                    .map(ZookeeperUtils::getData)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            SERVICE_ADDRESS_MAP.put(parentPath, data);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }

    private static byte[] getData(String path) {
        try {
            return zkClient.getData().forPath(path);
        } catch (Exception e) {
            log.error("get data from path [{}] fail", path);
        }
        return null;
    }

}
