package com.ligz.rpc.service.loadbalance;

import com.ligz.rpc.service.instance.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalance implements LoadBalance {
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstances) {
        int index = (counter.getAndIncrement() - 1) % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
