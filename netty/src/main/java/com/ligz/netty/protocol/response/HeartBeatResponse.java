package com.ligz.netty.protocol.response;


import com.ligz.netty.protocol.CustomProtocol;

import static com.ligz.netty.protocol.CommandConst.HEARTBEAT_RESPONSE;

public class HeartBeatResponse extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
