package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.SEND_GROUP_MESSAGE_REQUEST;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendGroupMessageRequest extends CustomProtocol {
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return SEND_GROUP_MESSAGE_REQUEST;
    }
}
