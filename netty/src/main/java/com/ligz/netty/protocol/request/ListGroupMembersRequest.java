package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.Command.LIST_GROUP_MEMBERS_REQUEST;


@Getter
@Setter
public class ListGroupMembersRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
