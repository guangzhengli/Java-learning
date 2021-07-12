package com.ligz.rpc.server;

import com.ligz.rpc.codec.MessageDecoder;
import com.ligz.rpc.codec.MessageEncoder;
import com.ligz.rpc.handler.InvocationRequestHandler;
import com.ligz.rpc.service.context.ServiceContext;
import com.ligz.rpc.service.instance.DefaultServiceInstance;
import com.ligz.rpc.service.instance.ServiceInstance;
import com.ligz.rpc.service.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashMap;

public class RpcServer implements AutoCloseable {

    private final String applicationName;

    private final int port;

    private final ServiceContext serviceContext;

    private final ServiceRegistry serviceRegistry;

    private final ServiceInstance localServiceInstance;

    private EventLoopGroup boss;

    private NioEventLoopGroup worker;

    private Channel channel;

    public RpcServer(String applicationName, int port) {
        this.applicationName = applicationName;
        this.port = port;
        this.serviceContext = ServiceContext.DEFAULT;
        this.serviceRegistry = ServiceRegistry.DEFAULT;
        this.localServiceInstance = createLocalServiceInstance();
    }

    private ServiceInstance createLocalServiceInstance() {
        DefaultServiceInstance serviceInstance = new DefaultServiceInstance("127.0.0.1", port);
        serviceInstance.setServiceName(applicationName);
        serviceInstance.setMetadata(new HashMap<>());
        return serviceInstance;
    }

    public RpcServer registerService(String serviceName, Object service) {
        serviceContext.registerService(serviceName, service);
        return this;
    }

    public RpcServer start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder());
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new InvocationRequestHandler(serviceContext));
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(port);
        registerServer();
        try {
            channel = channelFuture.sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void registerServer() {
        serviceRegistry.register(localServiceInstance);
    }

    private void deregisterServer() {
        serviceRegistry.deregister(localServiceInstance);
    }

    @Override
    public void close() throws Exception {
        deregisterServer();
        if (channel != null) {
            channel.close().sync();
        }
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }
}
