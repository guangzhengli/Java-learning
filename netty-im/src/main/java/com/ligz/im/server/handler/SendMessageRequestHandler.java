package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.exception.UserException;
import com.ligz.im.protocol.request.SendMessageRequest;
import com.ligz.im.protocol.response.MessageResponse;
import com.ligz.im.session.Session;
import com.ligz.im.session.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@ChannelHandler.Sharable
@Slf4j
public class SendMessageRequestHandler extends SimpleChannelInboundHandler<SendMessageRequest> {
    public static final SendMessageRequestHandler INSTANCE = new SendMessageRequestHandler();

    private SendMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessageRequest msg) {
        Session session = GlobalConfig.getSession();
        User user = session.getUser(ctx.channel()).orElseThrow(UserException::new);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setUserId(user.getUserId());
        messageResponse.setUserName(user.getUserName());
        messageResponse.setMessage(msg.getMessage());

        Optional<Channel> toUserChannel = session.getChannel(msg.getUserId());
        if (!toUserChannel.isPresent() || !session.hasLogin(toUserChannel.get())) {
            log.error("user is offline, send message error, userId:{}", msg.getUserId());
            throw new UserException("user is offline");
        }
        toUserChannel.get().writeAndFlush(messageResponse).addListener(future -> {
            if (future.isSuccess()) {
                log.info("send message is success");
            } else {
                log.error("send message is fail");
            }
        });
    }
}
