package com.dss.springboot.service.gateway.filter.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

    public ExampleGatewayFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return //new OrderedGatewayFilter(
                (exchange, chain) -> {
            LOGGER.info("executing pre gateway filter factory: {}", config.getMessage());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                Optional.ofNullable(config.getCookieValue()).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.getCookieName(), cookie).build());
                });

                LOGGER.info("executing post gateway filter factory: {}", config.getMessage());

            }));
        };
        //, 2);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "cookieName", "cookieValue");
    }

    @Override
    public String name() {
        return "ExampleCookie";
    }

    public static class Configuration {

        private String message;
        private String cookieName;
        private String cookieValue;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public String getCookieValue() {
            return cookieValue;
        }

        public void setCookieValue(String cookieValue) {
            this.cookieValue = cookieValue;
        }
    }
}
