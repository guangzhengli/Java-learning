package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.QUIT_GROUP_REQUEST;

@Setter
@Getter
public class QuitGroupRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
