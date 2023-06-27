package com.dss.springboot.service.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("Executing filter pre...");

        exchange.getRequest().mutate().headers(h -> h.add("Authorization", "123456"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            LOGGER.info("Executing filter post...");

            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Authorization")).ifPresent(value -> {
                exchange.getResponse().getHeaders().add("Authorization", value);
            });

            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
