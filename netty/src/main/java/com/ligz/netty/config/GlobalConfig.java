package com.ligz.netty.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ligz.netty.serialize.JSONSerialize;
import com.ligz.netty.serialize.Serialize;
import com.ligz.netty.session.Session;
import org.slf4j.LoggerFactory;

public class GlobalConfig {
    private static Session session;
    private static Serialize serialize;

    public static void init(Session session) {
        init(session, new JSONSerialize());
    }

    public static void init(Session session, Serialize serialize) {
        GlobalConfig.session = session;
        GlobalConfig.serialize = serialize;
        setLoggingLevel(Level.INFO);
    }

    public static void setLoggingLevel(Level level) {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(level);
    }

    public static Session getSession() {
        return session;
    }

    public static Serialize getSerialize() {
        return serialize;
    }
}
