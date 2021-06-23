package com.ligz.netty.protocol.response;

import lombok.Data;

import static com.ligz.netty.protocol.Command.QUIT_GROUP_RESPONSE;


@Data
public class QuitGroupResponse extends BaseResponse {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
