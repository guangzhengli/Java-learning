package com.ligz.rpc.client;

import com.ligz.rpc.InvocationRequest;
import com.ligz.rpc.handler.ExchangeFuture;
import com.ligz.rpc.service.instance.ServiceInstance;
import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.ligz.rpc.handler.ExchangeFuture.createExchangeFuture;
import static com.ligz.rpc.handler.ExchangeFuture.removeExchangeFuture;

@AllArgsConstructor
public class ServiceInvocationHandler implements InvocationHandler {
    private final String serviceName;

    private final RpcClient rpcClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationRequest invocationRequest = buildRequest(method, args);
        return execute(invocationRequest);
    }

    private Object execute(InvocationRequest request) {
        List<ServiceInstance> serviceInstances = rpcClient.getServiceRegistry().getServiceInstance(request.getServiceName());
        ServiceInstance serviceInstance = rpcClient.getLoadBalance().select(serviceInstances);

        ChannelFuture channelFuture = rpcClient.connect(serviceInstance);
        channelFuture.channel().writeAndFlush(request);

        ExchangeFuture exchangeFuture = createExchangeFuture(request);

        try {
            return exchangeFuture.get();
        } catch (Exception e) {
            removeExchangeFuture(request.getRequestId());
        }

        throw new IllegalStateException("Invocation failed!");
    }

    private InvocationRequest buildRequest(Method method, Object[] data) {
        return InvocationRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(data)
                .metadata(new HashMap<>())
                .build();
    }
}
