package com.ligz.rpc.demo;

import com.ligz.rpc.client.RpcClient;

public class ServiceConsumer {
    public static void main(String[] args) throws Exception {
        try(RpcClient rpcClient = new RpcClient()) {
            EchoService echoService = rpcClient.getService(EchoService.class.getName(), EchoService.class);
            System.out.println(echoService.echo("hello, rpc"));
        }
    }
}
