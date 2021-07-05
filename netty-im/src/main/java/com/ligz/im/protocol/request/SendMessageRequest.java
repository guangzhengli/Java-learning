package com.ligz.im.protocol.request;

import com.ligz.im.protocol.CustomProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ligz.im.protocol.CommandConst.SEND_MESSAGE_REQUEST;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest extends CustomProtocol {
    private String userId;
    private String message;

    @Override
    public Byte getCommand() {
        return SEND_MESSAGE_REQUEST;
    }
}
