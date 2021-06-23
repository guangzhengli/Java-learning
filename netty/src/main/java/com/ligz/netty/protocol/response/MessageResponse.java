package com.ligz.netty.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.Command.MESSAGE_RESPONSE;

@Getter
@Setter
public class MessageResponse extends BaseResponse {
    private String userId;
    private String userName;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
