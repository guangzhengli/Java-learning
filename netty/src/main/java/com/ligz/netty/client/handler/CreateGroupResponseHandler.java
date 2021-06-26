package com.ligz.netty.client.handler;

import com.ligz.netty.protocol.response.CreateGroupResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponse createGroupResponse) {
        log.info("create group success, groupId is:{}, group members{}",
                createGroupResponse.getGroupId(), createGroupResponse.getUserNameList());
    }
}
