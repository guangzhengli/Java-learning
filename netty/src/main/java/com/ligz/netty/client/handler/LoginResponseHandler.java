package com.ligz.netty.client.handler;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.protocol.response.LoginResponse;
import com.ligz.netty.session.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse loginResponse) {
        if (loginResponse.isSuccess()) {
            GlobalConfig.getSession().bind(
                    new User(loginResponse.getUserId(), loginResponse.getUserName()), ctx.channel());
            log.info("sign in success ,userId is :{}", loginResponse.getUserId());
        } else {
            log.error("login fail, error message:{}", loginResponse.getErrorMessage());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("client connect close...");
    }
}
