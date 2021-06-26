package com.ligz.netty.client.handler;

import com.ligz.netty.protocol.response.ListGroupMembersResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponse response) {
        log.info("members is :{} in the group groupId: {}", response.getUserList(), response.getGroupId());
    }
}
