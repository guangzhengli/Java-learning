package com.ligz.netty.protocol.response;

import lombok.Data;

import static com.ligz.netty.protocol.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponse extends BaseResponse {
    private String userId;
    private String userName;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
