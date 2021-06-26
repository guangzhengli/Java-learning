package com.ligz.netty.protocol.request;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.ligz.netty.protocol.CommandConst.SEND_MESSAGE_REQUEST;


@Getter
@Setter
@AllArgsConstructor
public class SendMessageRequest extends CustomProtocol {
    private String userId;
    private String message;

    @Override
    public Byte getCommand() {
        return SEND_MESSAGE_REQUEST;
    }
}
