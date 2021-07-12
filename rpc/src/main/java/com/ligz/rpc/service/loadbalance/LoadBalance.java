package com.ligz.rpc.service.loadbalance;

import com.ligz.rpc.service.instance.ServiceInstance;

import java.util.List;

import static com.ligz.rpc.util.ServiceLoaderUtil.loadDefault;

public interface LoadBalance {
    LoadBalance DEFAULT = loadDefault(LoadBalance.class);

    ServiceInstance select(List<ServiceInstance>serviceInstances);
}
