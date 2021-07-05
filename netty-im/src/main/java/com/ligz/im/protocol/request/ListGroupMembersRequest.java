package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.LIST_GROUP_MEMBERS_REQUEST;


@Getter
@Setter
public class ListGroupMembersRequest extends CustomProtocol {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
