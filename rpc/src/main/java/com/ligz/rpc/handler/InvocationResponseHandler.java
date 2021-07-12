package com.ligz.rpc.handler;

import com.ligz.rpc.InvocationResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.ligz.rpc.handler.ExchangeFuture.removeExchangeFuture;

public class InvocationResponseHandler extends SimpleChannelInboundHandler<InvocationResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvocationResponse response) throws Exception {
        String requestId = response.getRequestId();
        ExchangeFuture exchangeFuture = removeExchangeFuture(requestId);
        if (exchangeFuture != null) {
            Object result = response.getData();
            exchangeFuture.getPromise().setSuccess(result);
        }
    }
}
