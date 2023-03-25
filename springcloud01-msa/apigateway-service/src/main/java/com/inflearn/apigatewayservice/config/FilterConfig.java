package com.inflearn.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) { // applicatio.yml을 Java로 풀이한 것 (같은 역할)
        return builder.routes()
                .route(r -> r.path("/first-service/**") // path가 호출되면
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header") // request header에 적용
                                .addResponseHeader("first-response", "first-response-header") // response header에 적용
                        ) // filter 적용 후
                        .uri("http://localhost:8081") // uri로 이동한다.
                )
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header")
                        )
                        .uri("http://localhost:8082")
                )
                .build(); // route에 필요한 내용을 메모리에 반영
    }
}
