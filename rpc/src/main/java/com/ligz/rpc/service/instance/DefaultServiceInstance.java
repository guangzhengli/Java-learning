package com.ligz.rpc.service.instance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DefaultServiceInstance implements ServiceInstance {

    private String id;

    private String serviceName;

    private String host;

    private int port;

    private Map<String, String> metadata;

    public DefaultServiceInstance(String host, int port) {
        this.id = host + ":" + port;
        this.port = port;
        this.host = host;
    }
}
