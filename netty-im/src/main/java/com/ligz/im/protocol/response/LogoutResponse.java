package com.ligz.im.protocol.response;

import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.LOGOUT_RESPONSE;

@Getter
@Setter
public class LogoutResponse extends BaseResponse {
    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
