package com.ligz.rpc.serialize;

import static com.ligz.rpc.util.ServiceLoaderUtil.loadDefault;

public interface Serialize {
    Serialize DEFAULT = loadDefault(Serialize.class);

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
