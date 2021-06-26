package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.LOGOUT_REQUEST;


@Getter
@Setter
public class LogoutRequest extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
