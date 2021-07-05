package com.ligz.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CustomProtocol {
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
