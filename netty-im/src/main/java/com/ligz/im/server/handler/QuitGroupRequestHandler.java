package com.ligz.im.server.handler;

import com.ligz.im.config.GlobalConfig;
import com.ligz.im.protocol.request.QuitGroupRequest;
import com.ligz.im.protocol.response.QuitGroupResponse;
import com.ligz.im.session.Session;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;


@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequest> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequest request) {
        Session session = GlobalConfig.getSession();
        String groupId = request.getGroupId();

        ChannelGroup channelGroup = session.getChannelGroup(groupId)
                .orElseThrow(ChannelException::new);
        channelGroup.remove(ctx.channel());

        QuitGroupResponse response = new QuitGroupResponse();
        response.setGroupId(request.getGroupId());
        response.setSuccess(true);
        ctx.writeAndFlush(response);
    }
}
