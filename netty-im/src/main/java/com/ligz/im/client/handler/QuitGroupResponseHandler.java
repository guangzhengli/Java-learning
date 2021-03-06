package com.ligz.im.client.handler;

import com.ligz.im.protocol.response.QuitGroupResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponse response) {
        if (response.isSuccess()) {
            log.info("quit group success");
        } else {
            log.error("quit group fail, error message:{}", response.getErrorMessage());
        }

    }
}
