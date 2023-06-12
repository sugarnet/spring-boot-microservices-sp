package com.dss.springboot.service.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostElapsedTimeFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostElapsedTimeFilter.class);
    @Override
    public String filterType() {
        return "post";
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

        LOGGER.info("Entering to post filter");

        Long initTime = (Long) request.getAttribute("initTime");
        Long finalTime = System.currentTimeMillis();

        long elapsedTime = finalTime - initTime;

        LOGGER.info("Elapsed Time in SEG {}", elapsedTime/1000.00);
        LOGGER.info("Elapsed Time in MS {}", elapsedTime);

        Long time = System.currentTimeMillis();
        request.setAttribute("initTime", time);

        return null;
    }
}
