package com.ligz.netty.protocol.request;


import com.ligz.netty.protocol.CustomProtocol;

import static com.ligz.netty.protocol.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequest extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
