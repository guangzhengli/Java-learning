package com.ligz.rpc.client;

import com.ligz.rpc.codec.MessageDecoder;
import com.ligz.rpc.codec.MessageEncoder;
import com.ligz.rpc.handler.InvocationResponseHandler;
import com.ligz.rpc.service.instance.ServiceInstance;
import com.ligz.rpc.service.registry.ServiceRegistry;
import com.ligz.rpc.service.loadbalance.LoadBalance;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy;

public class RpcClient implements AutoCloseable {
    private final ServiceRegistry serviceRegistry;

    private final LoadBalance loadBalance;

    private final Bootstrap bootstrap;

    private final EventLoopGroup group;

    public RpcClient() {
        this(ServiceRegistry.DEFAULT, LoadBalance.DEFAULT);
    }

    public RpcClient(ServiceRegistry serviceRegistry, LoadBalance loadBalance) {
        this.serviceRegistry = serviceRegistry;
        this.loadBalance = loadBalance;
        this.bootstrap = new Bootstrap();
        this.group = new NioEventLoopGroup();
        this.bootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder());
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new InvocationResponseHandler());
                    }
                });
    }

    public <T> T getService(String serviceName, Class<T> serviceInterfaceClass) {
        return (T) Proxy.newProxyInstance(
                serviceInterfaceClass.getClassLoader(), new Class[]{serviceInterfaceClass},
                new ServiceInvocationHandler(serviceName, this));
    }

    public ChannelFuture connect(ServiceInstance serviceInstance) {
        ChannelFuture channelFuture = bootstrap.connect(serviceInstance.getHost(), serviceInstance.getPort());
        return channelFuture.awaitUninterruptibly();
    }

    protected ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    protected LoadBalance getLoadBalance() {
        return loadBalance;
    }

    @Override
    public void close() throws Exception {
        group.shutdownGracefully();
    }
}
