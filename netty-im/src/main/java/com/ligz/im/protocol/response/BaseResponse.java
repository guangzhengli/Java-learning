package com.ligz.im.protocol.response;

import com.ligz.im.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseResponse extends CustomProtocol {
    private boolean isSuccess;
    private String errorMessage;
}
