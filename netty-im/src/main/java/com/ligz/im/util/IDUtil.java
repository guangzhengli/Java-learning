package com.ligz.im.util;

import java.util.UUID;

public class IDUtil {
    public static String getId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
