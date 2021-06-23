package com.ligz.netty.config;

import com.ligz.netty.serialize.Serialize;
import com.ligz.netty.session.Session;

public class GlobalConfig {
    private static Session session;
    private static Serialize serialize;

    public static void init(Session session, Serialize serialize) {
        GlobalConfig.session = session;
        GlobalConfig.serialize = serialize;
    }

    public static Session getSession() {
        return session;
    }

    public static Serialize getSerialize() {
        return serialize;
    }
}
