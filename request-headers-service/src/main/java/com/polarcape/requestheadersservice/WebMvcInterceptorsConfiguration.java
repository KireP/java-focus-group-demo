package com.polarcape.requestheadersservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebMvcInterceptorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHeadersContextClearInterceptor()).addPathPatterns("/**");
    }

    private static class RequestHeadersContextClearInterceptor implements HandlerInterceptor {

        private static final Logger logger = LoggerFactory.getLogger(RequestHeadersContextClearInterceptor.class);

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
            logger.debug("Clearing request headers context");
            logger.debug("===============================================================================");

            RequestHeadersContextHolder.clearContext();
        }
    }
}
