package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.QUIT_GROUP_REQUEST;

@Setter
@Getter
public class QuitGroupRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
