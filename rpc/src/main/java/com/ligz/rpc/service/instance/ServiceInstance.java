package com.ligz.rpc.service.instance;

import java.util.Map;

public interface ServiceInstance {
    String getId();

    String getServiceName();

    String getHost();

    int getPort();

    Map<String, String> getMetadata();
}
