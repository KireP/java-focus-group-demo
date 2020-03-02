package com.polarcape.requestheadersservice;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestHeadersContext {

    public static final String CORRELATION_ID = "pc-correlation-id";
    public static final String AUTHENTICATION_TOKEN = "pc-authentication-token";

    private String correlationId;
    private String authenticationToken;
}
