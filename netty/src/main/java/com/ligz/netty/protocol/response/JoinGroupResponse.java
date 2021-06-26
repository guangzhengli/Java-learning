package com.ligz.netty.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.JOIN_GROUP_RESPONSE;

@Getter
@Setter
public class JoinGroupResponse extends BaseResponse {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
