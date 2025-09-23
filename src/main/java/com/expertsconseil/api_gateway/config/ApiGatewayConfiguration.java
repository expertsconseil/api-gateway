package com.expertsconseil.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader","MyUri")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/user/**")
                        .uri("lb://user-service"))
                .route(p -> p.path("/user-newUrl/**")
                        .filters(f -> f.rewritePath("/user-newUrl/(?<segment>.*)", "/user/${segment}"))
                        .uri("lb://user-service"))
                .route(p -> p.path("/quiz/**")
                        .uri("lb://quiz-service"))
                .build();

    }
}
