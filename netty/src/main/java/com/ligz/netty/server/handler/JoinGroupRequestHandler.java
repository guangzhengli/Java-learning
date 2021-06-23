package com.ligz.netty.server.handler;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.protocol.request.JoinGroupRequest;
import com.ligz.netty.protocol.response.JoinGroupResponse;
import com.ligz.netty.session.Session;
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
