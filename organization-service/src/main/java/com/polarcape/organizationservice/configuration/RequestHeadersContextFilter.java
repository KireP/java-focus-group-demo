package com.polarcape.organizationservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestHeadersContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeadersContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        RequestHeadersContext context = RequestHeadersContextHolder.getContext();
        context.setCorrelationId(httpServletRequest.getHeader(RequestHeadersContext.CORRELATION_ID));
        context.setAuthenticationToken(httpServletRequest.getHeader(RequestHeadersContext.AUTHENTICATION_TOKEN));

        logger.debug("Incoming request headers context: {}", RequestHeadersContextHolder.getContext().toString());

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
