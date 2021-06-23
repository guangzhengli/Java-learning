package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Data;

import static com.ligz.netty.protocol.Command.LOGOUT_REQUEST;


@Data
public class LogoutRequest extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
