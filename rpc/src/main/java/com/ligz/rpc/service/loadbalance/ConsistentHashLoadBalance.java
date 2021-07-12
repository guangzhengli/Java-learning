package com.ligz.rpc.service.loadbalance;

import com.ligz.rpc.service.instance.ServiceInstance;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConsistentHashLoadBalance implements LoadBalance {
    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();

    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstances) {
        int hashCode = serviceInstances.hashCode();
        String key = serviceInstances.get(0).getServiceName();
        ConsistentHashSelector selector = selectors.get(key);
        if (selector == null || selector.identityHashCode != hashCode) {
            selectors.put(key, new ConsistentHashSelector(serviceInstances, 160, hashCode));
            selector = selectors.get(key);
        }
        return selector.select(key);
    }

    private static final class ConsistentHashSelector {

        private final TreeMap<Long, ServiceInstance> virtualInvokers;

        private final int identityHashCode;

        ConsistentHashSelector(List<ServiceInstance> serviceInstances, int replicaNumber, int identityHashCode) {
            this.virtualInvokers = new TreeMap<>();
            this.identityHashCode = identityHashCode;

            for (ServiceInstance serviceInstance : serviceInstances) {
                for (int i = 0; i < replicaNumber / 4; i++) {
                    byte[] digest = md5(serviceInstance.getId() + i);
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        virtualInvokers.put(m, serviceInstance);
                    }
                }
            }
        }

        static byte[] md5(String key) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                md.update(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }

            return md.digest();
        }

        public ServiceInstance select(String rpcServiceKey) {
            byte[] digest = md5(rpcServiceKey);
            return selectForKey(hash(digest, 0));
        }

        private ServiceInstance selectForKey(long hash) {
            Map.Entry<Long, ServiceInstance> entry = virtualInvokers.ceilingEntry(hash);
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }

        private long hash(byte[] digest, int number) {
            return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                    | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                    | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                    | (digest[number * 4] & 0xFF))
                    & 0xFFFFFFFFL;
        }
    }
}
