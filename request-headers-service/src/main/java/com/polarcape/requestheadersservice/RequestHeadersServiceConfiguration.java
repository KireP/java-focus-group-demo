package com.polarcape.requestheadersservice;

import feign.RequestInterceptor;
import feign.RequestTemplate;
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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Configuration
public class RequestHeadersServiceConfiguration {

    @Bean
    public Filter getFilter() {
        return new RequestHeadersContextFilter();
    }

    private static class RequestHeadersContextFilter implements Filter {

        private static final Logger logger = LoggerFactory.getLogger(RequestHeadersContextFilter.class);

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            RequestHeadersContext context = new RequestHeadersContext(
                    httpServletRequest.getHeader(RequestHeadersContext.CORRELATION_ID),
                    httpServletRequest.getHeader(RequestHeadersContext.AUTHENTICATION_TOKEN)
            );
            RequestHeadersContextHolder.setContext(context);

            logger.debug("Incoming request headers context: {}", RequestHeadersContextHolder.getContext().toString());
            logger.debug("===============================================================================");

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

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
            RequestHeadersContext context = RequestHeadersContextHolder.getContext();

            logger.debug("REST TEMPLATE CLIENT - Outgoing request headers context: {}", context.toString());
            logger.debug("===============================================================================");

            HttpHeaders headers = httpRequest.getHeaders();
            headers.add(RequestHeadersContext.CORRELATION_ID, context.getCorrelationId());
            headers.add(RequestHeadersContext.AUTHENTICATION_TOKEN, context.getAuthenticationToken());

            return clientHttpRequestExecution.execute(httpRequest, body);
        }
    }

    @Bean
    public RequestInterceptor getFeignRequestInterceptor() {
        return new RequestHeadersContextFeignInterceptor();
    }

    private static class RequestHeadersContextFeignInterceptor implements RequestInterceptor {

        private static final Logger logger = LoggerFactory.getLogger(RequestHeadersContextFeignInterceptor.class);

        @Override
        public void apply(RequestTemplate requestTemplate) {
            RequestHeadersContext context = RequestHeadersContextHolder.getContext();

            logger.debug("FEIGN CLIENT - Outgoing request headers context FEIGN CLIENT: {}", context.toString());
            logger.debug("===============================================================================");

            requestTemplate.header(RequestHeadersContext.CORRELATION_ID, context.getCorrelationId());
            requestTemplate.header(RequestHeadersContext.AUTHENTICATION_TOKEN, context.getAuthenticationToken());
        }
    }

}
