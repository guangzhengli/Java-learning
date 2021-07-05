package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.protocol.request.JoinGroupRequest;
import com.ligz.im.protocol.response.JoinGroupResponse;
import com.ligz.im.session.Session;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequest> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequest request) {
        Session session = GlobalConfig.getSession();
        String groupId = request.getGroupId();

        ChannelGroup channelGroup = session.getChannelGroup(groupId).orElseThrow(ChannelException::new);
        channelGroup.add(ctx.channel());

        JoinGroupResponse response = new JoinGroupResponse();
        response.setSuccess(true);
        response.setGroupId(groupId);
        ctx.writeAndFlush(response);
    }
}
