package com.ligz.netty.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.QUIT_GROUP_RESPONSE;


@Setter
@Getter
public class QuitGroupResponse extends BaseResponse {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
