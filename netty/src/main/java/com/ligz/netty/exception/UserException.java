package com.ligz.netty.exception;

public class UserException extends RuntimeException {
    public UserException() {
        super("user not fount");
    }

    public UserException(String message) {
        super(message);
    }
}
