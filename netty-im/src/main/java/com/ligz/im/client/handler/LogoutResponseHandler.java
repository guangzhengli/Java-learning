package com.ligz.im.client.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.protocol.response.LogoutResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponse logoutResponse) {
        GlobalConfig.getSession().unBind(ctx.channel());
        log.info("sign out success");
    }
}
