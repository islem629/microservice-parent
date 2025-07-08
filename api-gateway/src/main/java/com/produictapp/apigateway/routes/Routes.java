package com.produictapp.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayDelegatingRouterFunction;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> productservice(Routes routes, HandlerFunction handlerFunction) {
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/produits"), HandlerFunctions.http("http://localhost:8083"))
                .build();


    }

    @Bean
    public RouterFunction<ServerResponse> orderservice(Routes routes, HandlerFunction handlerFunction) {
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/order"), HandlerFunctions.http("http://localhost:8081"))
                .build();


    }
    @Bean
    public RouterFunction<ServerResponse> inventoryservice(Routes routes, HandlerFunction handlerFunction) {
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .build();


    }
}
