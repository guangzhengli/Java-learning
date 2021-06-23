package com.ligz.netty.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.Command.LOGOUT_RESPONSE;

@Getter
@Setter
public class LogoutResponse extends BaseResponse {
    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
