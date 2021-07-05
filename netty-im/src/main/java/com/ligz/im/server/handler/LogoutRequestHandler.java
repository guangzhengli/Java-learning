package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.protocol.request.LogoutRequest;
import com.ligz.im.protocol.response.LogoutResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequest> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() { }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequest msg) {
        GlobalConfig.getSession().unBind(ctx.channel());
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setSuccess(true);
        ctx.writeAndFlush(logoutResponse);
    }
}
