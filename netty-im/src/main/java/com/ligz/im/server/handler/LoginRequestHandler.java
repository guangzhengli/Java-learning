package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.protocol.request.LoginRequest;
import com.ligz.im.protocol.response.LoginResponse;
import com.ligz.im.session.Session;
import com.ligz.im.session.User;
import com.ligz.im.util.IDUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() { }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest loginRequest) {
        Session session = GlobalConfig.getSession();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setVersion(loginRequest.getVersion());
        loginResponse.setUserName(loginRequest.getUserName());

        if (valid(loginRequest)) {
            loginResponse.setSuccess(true);
            String userId = IDUtil.getId();
            loginResponse.setUserId(userId);
            log.info("{} sign in success", loginRequest.getUserName());
            session.bind(new User(userId, loginRequest.getUserName()), ctx.channel());
        } else {
            loginResponse.setErrorMessage("password is not correct");
            loginResponse.setSuccess(false);
            log.warn("{} sign in fail", loginRequest.getUserName());
        }
        ctx.writeAndFlush(loginResponse);
    }

    private boolean valid(LoginRequest loginRequest) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        GlobalConfig.getSession().unBind(ctx.channel());
    }
}
