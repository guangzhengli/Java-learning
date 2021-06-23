package com.ligz.netty.server.handler;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.exception.ChannelException;
import com.ligz.netty.protocol.request.ListGroupMembersRequest;
import com.ligz.netty.protocol.response.ListGroupMembersResponse;
import com.ligz.netty.session.Session;
import com.ligz.netty.session.User;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequest> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequest request) {
        Session session = GlobalConfig.getSession();
        String groupId = request.getGroupId();
        List<User> users = session.getChannelGroup(groupId)
                .orElseThrow(ChannelException::new)
                .stream()
                .map(session::getUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        ListGroupMembersResponse response = new ListGroupMembersResponse();
        response.setGroupId(groupId);
        response.setUserList(users);
        ctx.writeAndFlush(response);
    }
}
