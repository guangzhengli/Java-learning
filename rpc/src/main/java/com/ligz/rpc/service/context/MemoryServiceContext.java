package com.ligz.rpc.service.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryServiceContext implements ServiceContext {
    private final Map<String, Object> serviceContextMap = new ConcurrentHashMap<>();

    @Override
    public boolean registerService(String serviceName, Object service) {
        return serviceContextMap.putIfAbsent(serviceName, service) == null;
    }

    @Override
    public Object getService(String serviceName) {
        return serviceContextMap.get(serviceName);
    }
}
