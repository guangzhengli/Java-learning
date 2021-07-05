package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!GlobalConfig.getSession().hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
