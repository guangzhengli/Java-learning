package com.ligz.im.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.MESSAGE_RESPONSE;

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
