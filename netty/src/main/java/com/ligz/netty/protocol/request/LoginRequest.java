package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.Command.LOGIN_REQUEST;


@Setter
@Getter
public class LoginRequest extends CustomProtocol {
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
