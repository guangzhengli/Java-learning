package com.ligz.rpc.service.registry;

import com.ligz.rpc.serialize.Serialize;
import com.ligz.rpc.service.instance.DefaultServiceInstance;
import com.ligz.rpc.service.instance.ServiceInstance;
import com.ligz.rpc.util.ZookeeperUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.ligz.rpc.util.ZookeeperUtils.ZK_REGISTER_ROOT_PATH;

@Slf4j
public class ZookeeperServiceRegistry implements ServiceRegistry {
    @Override
    public void register(ServiceInstance serviceInstance) {
        log.info("register to zookeeper, serviceInstance:{}", serviceInstance);
        ZookeeperUtils.createPersistentNode(
                getPath(serviceInstance), Serialize.DEFAULT.serialize(serviceInstance));
    }

    @Override
    public void deregister(ServiceInstance serviceInstance) {
        ZookeeperUtils.clearRegistry(getPath(serviceInstance));
    }

    @Override
    public List<ServiceInstance> getServiceInstance(String serviceName) {
        List<byte[]> data = ZookeeperUtils.getChildrenNodes(ZK_REGISTER_ROOT_PATH + "/" + serviceName);
        return data.stream()
                .map(bytes -> Serialize.DEFAULT.deserialize(DefaultServiceInstance.class, bytes))
                .collect(Collectors.toList());
    }

    private String getPath(ServiceInstance serviceInstance) {
        return ZK_REGISTER_ROOT_PATH + "/" + serviceInstance.getServiceName() + "/" + serviceInstance.getId();
    }
}
