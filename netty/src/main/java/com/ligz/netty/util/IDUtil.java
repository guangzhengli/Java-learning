package com.ligz.netty.util;

import java.util.concurrent.atomic.AtomicLong;

public class IDUtil {
    private static AtomicLong requestId;

    public static Long getRequestId() {
        return requestId.getAndIncrement();
    }
}
