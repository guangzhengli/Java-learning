package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.ligz.im.protocol.CommandConst.CREATE_GROUP_REQUEST;

@Getter
@Setter
public class CreateGroupRequest extends CustomProtocol {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
