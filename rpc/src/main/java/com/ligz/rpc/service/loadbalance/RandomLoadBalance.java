package com.ligz.rpc.service.loadbalance;

import com.ligz.rpc.service.instance.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstances) {
        int size = serviceInstances.size();
        int index = ThreadLocalRandom.current().nextInt(size);
        return serviceInstances.get(index);
    }
}
