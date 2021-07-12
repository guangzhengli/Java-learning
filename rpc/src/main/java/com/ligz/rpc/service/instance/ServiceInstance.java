package com.ligz.rpc.service.instance;

import java.io.Serializable;
import java.util.Map;

public interface ServiceInstance extends Serializable {
    String getId();

    String getServiceName();

    String getHost();

    int getPort();

    Map<String, String> getMetadata();
}
