package com.ligz.netty.server;

import com.ligz.netty.codec.CustomProtocolHandler;
import com.ligz.netty.codec.Spliter;
import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.handler.IMIdleStateHandler;
import com.ligz.netty.serialize.JSONSerialize;
import com.ligz.netty.serialize.Serialize;
import com.ligz.netty.server.handler.AuthHandler;
import com.ligz.netty.server.handler.HeartBeatRequestHandler;
import com.ligz.netty.server.handler.IMHandler;
import com.ligz.netty.server.handler.LoginRequestHandler;
import com.ligz.netty.session.MemorySession;
import com.ligz.netty.session.Session;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        Session session = MemorySession.getInstance();
        Serialize serialize = new JSONSerialize();
        GlobalConfig.init(session, serialize);

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new IMIdleStateHandler());
                        p.addLast(new Spliter());
                        p.addLast(CustomProtocolHandler.INSTANCE);
                        p.addLast(LoginRequestHandler.INSTANCE);
                        p.addLast(HeartBeatRequestHandler.INSTANCE);
                        p.addLast(AuthHandler.INSTANCE);
                        p.addLast(IMHandler.INSTANCE);
                    }
                });
        serverBootstrap.bind(PORT).addListener(future -> {
            if (future.isSuccess()) {
                log.info("port bind success, Server start success");
            } else {
                log.error("port bind fail, Server start fail");
            }
        });

    }
}
