package com.ligz.rpc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InvocationRequest {
    private String requestId;

    private String serviceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

    private Map<String, Object> metadata;
}
