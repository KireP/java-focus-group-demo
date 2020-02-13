package com.polarcape.licensingservice.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestHeadersContext {

    public static final String CORRELATION_ID = "pc-correlation-id";
    public static final String AUTHENTICATION_TOKEN = "pc-authentication-token";

    private String correlationId;
    private String authenticationToken;
}
