package com.inflearn.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> { // CustomFilter는 AbstractGatewayFilterFactory를 상속받아서 사용
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) { // 작업할 내용
        // Global Pre Filter
        return (exchange, chain) -> { // PRE 작업
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            if(config.isPreLogger()){ // Pre Logger가 작동되어야 한다면
                log.info("Global Filter Start : request id -> {}", request.getId());
            }

            // Global Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // POST 작업
                // Mono: 데이터를 단일값으로 준다.
                if(config.isPostLogger()){ // Post Logger가 작동되어야 한다면
                    log.info("Global Filter End : response id -> {}", response.getStatusCode());
                }
            }));
        };
    }

    @Data
    public static class Config {
        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
