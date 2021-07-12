package com.ligz.rpc.demo;

import com.ligz.rpc.server.RpcServer;

public class ServiceProvider {
    public static void main(String[] args) throws Exception {
        try(RpcServer server = new RpcServer(EchoService.class.getName(), 8000)) {
            server.registerService(EchoService.class.getName(), new DefaultEchoService());
            server.start();
        }
    }
}
