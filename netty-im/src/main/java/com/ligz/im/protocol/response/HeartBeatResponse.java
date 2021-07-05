package com.ligz.im.protocol.response;


import com.ligz.im.protocol.CustomProtocol;

import static com.ligz.im.protocol.CommandConst.HEARTBEAT_RESPONSE;

public class HeartBeatResponse extends CustomProtocol {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
