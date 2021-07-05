package com.ligz.im.protocol.response;

import com.ligz.im.session.User;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.GROUP_MESSAGE_RESPONSE;


@Setter
@Getter
public class GroupMessageResponse extends BaseResponse {
    private String groupId;
    private User user;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
