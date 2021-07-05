package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.LOGIN_REQUEST;


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
