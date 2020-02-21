package com.polarcape.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.polarcape.zuulserver.util.FilterUtilConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrackingFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Override
    public String filterType() {
        return FilterUtilConstants.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        logger.debug("Correlation ID: {}", RequestContext.getCurrentContext().getRequest().getHeader(FilterUtilConstants.CORRELATION_ID));
        logger.debug("Authentication token: {}", RequestContext.getCurrentContext().getRequest().getHeader(FilterUtilConstants.AUTHENTICATION_TOKEN));
        logger.debug("===============================================================================");

        return null;
    }
}
