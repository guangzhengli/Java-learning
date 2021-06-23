package com.ligz.netty.exception;

public class ChannelException extends RuntimeException {
    public ChannelException() {
        super("channel not fount");
    }

    public ChannelException(String message) {
        super(message);
    }
}
