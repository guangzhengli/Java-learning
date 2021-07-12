package com.ligz.rpc.service.registry;

import com.ligz.rpc.service.instance.ServiceInstance;

import java.util.List;

import static com.ligz.rpc.util.ServiceLoaderUtil.loadDefault;

public interface ServiceRegistry {
    ServiceRegistry DEFAULT = loadDefault(ServiceRegistry.class);

    void register(ServiceInstance serviceInstance);

    void deregister(ServiceInstance serviceInstance);

    List<ServiceInstance> getServiceInstance(String serviceName);
}
