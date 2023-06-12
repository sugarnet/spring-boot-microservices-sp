package com.dss.springboot.service.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreElapsedTimeFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreElapsedTimeFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        LOGGER.info("{} routed request to {}", request.getMethod(), request.getRequestURL());

        Long time = System.currentTimeMillis();
        request.setAttribute("initTime", time);

        return null;
    }
}
