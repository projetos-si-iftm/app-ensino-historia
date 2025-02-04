package com.iftm.gateway_api;
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
        .route("questoes-router", r -> r.path("/questoes/**")
            .uri("http://localhost:8080/"))
        .route("respostas_route", r -> r.path("/respostas/**")
            .uri("http://localhost:8081/"))
        .route("autenticacao_route", r -> r.path("/autenticacao/**")
            .uri("http://localhost:8082/"))
        .build();
    }

}