package com.ligz.im.serialize;

public interface Serialize {
    byte getSerializeType();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
