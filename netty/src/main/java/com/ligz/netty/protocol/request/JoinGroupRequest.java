package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.JOIN_GROUP_REQUEST;


@Getter
@Setter
public class JoinGroupRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
