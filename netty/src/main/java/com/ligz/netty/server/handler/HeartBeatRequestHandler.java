package com.ligz.netty.server.handler;

import com.ligz.netty.protocol.request.HeartBeatRequest;
import com.ligz.netty.protocol.response.HeartBeatResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequest> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequest request) {
        ctx.writeAndFlush(new HeartBeatResponse());
    }
}
