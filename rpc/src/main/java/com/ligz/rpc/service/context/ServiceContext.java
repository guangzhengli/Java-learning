package com.ligz.rpc.service.context;

import static com.ligz.rpc.util.ServiceLoaderUtil.loadDefault;

public interface ServiceContext {
    ServiceContext DEFAULT = loadDefault(ServiceContext.class);

    boolean registerService(String serviceName, Object service);

    Object getService(String serviceName);
}
