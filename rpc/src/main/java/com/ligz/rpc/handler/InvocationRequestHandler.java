package com.ligz.rpc.handler;

import com.ligz.rpc.InvocationRequest;
import com.ligz.rpc.InvocationResponse;
import com.ligz.rpc.service.context.ServiceContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.reflect.MethodUtils;

@AllArgsConstructor
@Slf4j
public class InvocationRequestHandler extends SimpleChannelInboundHandler<InvocationRequest> {
    private final ServiceContext serviceContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvocationRequest msg) {
        String serviceName = msg.getServiceName();

        Object service = serviceContext.getService(serviceName);
        Object data = null;
        String errMsg = null;
        try {
            data = MethodUtils.invokeMethod(service, msg.getMethodName(), msg.getParameters(), msg.getParameterTypes());
            log.info("invocation request:{}, invocation response:{}", msg, data);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        InvocationResponse response = InvocationResponse.builder()
                .requestId(msg.getRequestId())
                .data(data)
                .errorMessage(errMsg)
                .build();

        ctx.writeAndFlush(response);
    }
}
