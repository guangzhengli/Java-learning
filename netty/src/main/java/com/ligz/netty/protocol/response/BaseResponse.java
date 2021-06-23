package com.ligz.netty.protocol.response;

import com.ligz.netty.protocol.CustomProtocol;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseResponse extends CustomProtocol {
    private boolean isSuccess;
    private String errorMessage;
}
