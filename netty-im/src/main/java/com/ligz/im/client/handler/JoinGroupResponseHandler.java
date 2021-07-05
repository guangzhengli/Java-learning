package com.ligz.im.client.handler;

import com.ligz.im.protocol.response.JoinGroupResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponse response) {
        if (response.isSuccess()) {
            log.info("join the group success, groupId:{}",response.getGroupId());
        } else {
            log.error("join the group fail groupId:{}, error message:{}",
                    response.getGroupId(), response.getErrorMessage());
        }
    }
}
