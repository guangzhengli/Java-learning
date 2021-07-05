package com.ligz.im.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.JOIN_GROUP_RESPONSE;

@Getter
@Setter
public class JoinGroupResponse extends BaseResponse {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
