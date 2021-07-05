package com.ligz.im.protocol.response;

import com.ligz.im.session.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.ligz.im.protocol.CommandConst.LIST_GROUP_MEMBERS_RESPONSE;


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
