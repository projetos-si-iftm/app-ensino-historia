package com.iftm.gateway_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class GatewayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApiApplication.class, args);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("frontend-router", r -> r.path("../frontend-react/frontend/**")
            .uri("http://localhost:8080/"))
        .route("questoes-router", r -> r.path("/questoes-api/**")
            .uri("http://localhost:8081/"))
        .route("respostas_route", r -> r.path("/respostas-api/**")
            .uri("http://localhost:8082/"))
        .route("autenticacao_route", r -> r.path("/autenticacao/**")
            .uri("http://localhost:8083/"))
        .build();
    }

}