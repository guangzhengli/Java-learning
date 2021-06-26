package com.ligz.netty.client.handler;

import com.ligz.netty.protocol.response.GroupMessageResponse;
import com.ligz.netty.session.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponse response) {
        String fromGroupId = response.getGroupId();
        User fromUser = response.getUser();
        log.info("give the message from group:{}, message is {}, send by {}",
                fromGroupId, response.getMessage(), fromUser);
    }
}
