package com.ligz.im.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.LOGIN_RESPONSE;

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
