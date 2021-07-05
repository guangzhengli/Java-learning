package com.ligz.im.protocol.request;


import com.ligz.im.protocol.CustomProtocol;

import static com.ligz.im.protocol.CommandConst.HEARTBEAT_REQUEST;

public class HeartBeatRequest extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
