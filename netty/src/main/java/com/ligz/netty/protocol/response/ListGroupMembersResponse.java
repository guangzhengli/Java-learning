package com.ligz.netty.protocol.response;

import com.ligz.netty.session.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.ligz.netty.protocol.CommandConst.LIST_GROUP_MEMBERS_RESPONSE;


@Getter
@Setter
public class ListGroupMembersResponse extends BaseResponse {

    private String groupId;

    private List<User> userList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
