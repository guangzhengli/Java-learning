package com.ligz.netty.server.handler;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.exception.ChannelException;
import com.ligz.netty.exception.UserException;
import com.ligz.netty.protocol.request.SendGroupMessageRequest;
import com.ligz.netty.protocol.response.GroupMessageResponse;
import com.ligz.netty.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class SendGroupMessageRequestHandler extends SimpleChannelInboundHandler<SendGroupMessageRequest> {
    public static final SendGroupMessageRequestHandler INSTANCE = new SendGroupMessageRequestHandler();

    private SendGroupMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageRequest request) {
        Session session = GlobalConfig.getSession();
        String groupId = request.getGroupId();
        GroupMessageResponse response = new GroupMessageResponse();
        response.setGroupId(groupId);
        response.setMessage(request.getMessage());
        response.setUser(session.getUser(ctx.channel()).orElseThrow(UserException::new));

        ChannelGroup channelGroup = session.getChannelGroup(groupId).orElseThrow(ChannelException::new);
        channelGroup.writeAndFlush(response);
    }
}
