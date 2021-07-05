package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.LOGOUT_REQUEST;


@Getter
@Setter
public class LogoutRequest extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
