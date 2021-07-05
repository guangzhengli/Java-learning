package com.ligz.im.protocol.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.ligz.im.protocol.CommandConst.CREATE_GROUP_RESPONSE;


@Getter
@Setter
public class CreateGroupResponse extends BaseResponse {
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
