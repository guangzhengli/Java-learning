package com.ligz.netty.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.LOGIN_RESPONSE;

@Setter
@Getter
public class LoginResponse extends BaseResponse {
    private String userId;

    private String userName;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
