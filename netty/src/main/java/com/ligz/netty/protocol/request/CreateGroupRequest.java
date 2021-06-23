package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.ligz.netty.protocol.Command.CREATE_GROUP_REQUEST;

@Getter
@Setter
public class CreateGroupRequest extends CustomProtocol {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
