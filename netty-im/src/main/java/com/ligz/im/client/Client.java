package com.ligz.im.client;

import com.ligz.im.client.command.CommandManager;
import com.ligz.im.client.command.LoginCommand;
import com.ligz.im.client.handler.CreateGroupResponseHandler;
import com.ligz.im.client.handler.GroupMessageResponseHandler;
import com.ligz.im.client.handler.HeartBeatTimerHandler;
import com.ligz.im.client.handler.JoinGroupResponseHandler;
import com.ligz.im.client.handler.ListGroupMembersResponseHandler;
import com.ligz.im.client.handler.LoginResponseHandler;
import com.ligz.im.client.handler.LogoutResponseHandler;
import com.ligz.im.client.handler.MessageResponseHandler;
import com.ligz.im.client.handler.QuitGroupResponseHandler;
import com.ligz.im.codec.CustomProtocolDecoder;
import com.ligz.im.codec.CustomProtocolEncoder;
import com.ligz.im.codec.Spliter;
import com.ligz.im.config.GlobalConfig;
import com.ligz.im.handler.IMIdleStateHandler;
import com.ligz.im.session.MemorySession;
import com.ligz.im.session.Session;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Client {
    private static final int MAX_RETRY = 5;
    private static final int PORT = 8000;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        Session session = MemorySession.getInstance();
        GlobalConfig.init(session);
        NioEventLoopGroup work = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new IMIdleStateHandler());
                        p.addLast(new Spliter());
                        p.addLast(new CustomProtocolDecoder());

                        p.addLast(new LoginResponseHandler());
                        p.addLast(new MessageResponseHandler());
                        p.addLast(new CreateGroupResponseHandler());
                        p.addLast(new JoinGroupResponseHandler());
                        p.addLast(new QuitGroupResponseHandler());
                        p.addLast(new ListGroupMembersResponseHandler());
                        p.addLast(new GroupMessageResponseHandler());
                        p.addLast(new LogoutResponseHandler());

                        p.addLast(new CustomProtocolEncoder());
                        p.addLast(new HeartBeatTimerHandler());
                    }
                });
        connect(bootstrap, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(HOST, PORT).addListener(future -> {
            if (future.isSuccess()) {
                log.info("client connect server success, start console...");
                Channel channel = ((ChannelFuture)future).channel();
                startConsole(channel);
            } else if (retry != 0) {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                log.error("connect fail, retry {} time", order);
                bootstrap.config().group().schedule(() -> connect(bootstrap, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsole(Channel channel) {
        CommandManager commandManager = new CommandManager();
        LoginCommand loginCommand = new LoginCommand();
        Scanner scanner = new Scanner(System.in);
        Thread console = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!GlobalConfig.getSession().hasLogin(channel)) {
                    loginCommand.exec(scanner, channel);
                } else {
                    commandManager.exec(scanner, channel);
                }
            }
        });
        console.start();
    }
}
