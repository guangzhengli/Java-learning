package com.ligz.netty.client.handler;

import com.ligz.netty.protocol.response.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse messageResponse) {
        log.info("{}-{}: {}", messageResponse.getUserId(), messageResponse.getUserName(), messageResponse.getMessage());
    }
}
