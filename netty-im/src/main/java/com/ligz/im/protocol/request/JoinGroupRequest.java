package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.JOIN_GROUP_REQUEST;


@Getter
@Setter
public class JoinGroupRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
