package com.polarcape.organizationservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new RequestHeadersContextInterceptor());
        return restTemplate;
    }

    private static class RequestHeadersContextInterceptor implements ClientHttpRequestInterceptor {

        private static final Logger logger = LoggerFactory.getLogger(RequestHeadersContextInterceptor.class);

        @Override
        @NonNull
        public ClientHttpResponse intercept(@NonNull HttpRequest httpRequest,
                                            @NonNull byte[] body,
                                            @NonNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            HttpHeaders headers = httpRequest.getHeaders();

            RequestHeadersContext context = RequestHeadersContextHolder.getContext();

            logger.debug("Outgoing request headers context: {}", context.toString());

            headers.add(RequestHeadersContext.CORRELATION_ID, context.getCorrelationId());
            headers.add(RequestHeadersContext.AUTHENTICATION_TOKEN, context.getAuthenticationToken());

            return clientHttpRequestExecution.execute(httpRequest, body);
        }
    }
}
