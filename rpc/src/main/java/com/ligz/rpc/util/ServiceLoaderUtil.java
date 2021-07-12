package com.ligz.rpc.util;

import java.util.ServiceLoader;

public class ServiceLoaderUtil {
    public static <T> T loadDefault(Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass).iterator().next();
    }
}
