package com.moghoneim.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class Routes {
    @Bean
    RouterFunction<ServerResponse> usersServiceRoute() {
        return GatewayRouterFunctions.route("users_service")
                .route(RequestPredicates.path("/api/v1/users/**"),
                        HandlerFunctions.http("http://localhost:8082/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("usersServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                )).build();
    }

    @Bean
    RouterFunction<ServerResponse> userServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("user_service_swagger")
                .route(RequestPredicates.path("/aggregate/user-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("usersServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .filter(FilterFunctions.setPath("/api-docs"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> guestsServiceRoute() {
        return GatewayRouterFunctions.route("guests_service")
                .route(RequestPredicates.path("/api/v1/guests/**"),
                        HandlerFunctions.http("http://localhost:8081/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("guestsServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                )).build();
    }

    @Bean
    RouterFunction<ServerResponse> guestsServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("guests_service_swagger")
                .route(RequestPredicates.path("/aggregate/guests-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("guestsServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .filter(FilterFunctions.setPath("/api-docs"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> rsvpServiceRoute() {
        return GatewayRouterFunctions.route("rsvp_service")
                .route(RequestPredicates.path("/api/v1/rsvp/**"),
                        HandlerFunctions.http("http://localhost:8083/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("rsvpServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                )).build();
    }

    @Bean
    RouterFunction<ServerResponse> rsvpServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("rsvp_service_swagger")
                .route(RequestPredicates.path("/aggregate/rsvp-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8083"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("rsvpServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .filter(FilterFunctions.setPath("/api-docs"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> vendorsServiceRoute() {
        return GatewayRouterFunctions.route("vendors_service")
                .route(RequestPredicates.path("/api/v1/vendors/**"),
                        HandlerFunctions.http("http://localhost:8085/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("vendorsServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();


    }

    @Bean
    RouterFunction<ServerResponse> vendorsServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("vendors_service_swagger")
                .route(RequestPredicates.path("/aggregate/vendors-service/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8085"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("vendorsServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .filter(FilterFunctions.setPath("/api-docs"))
                .build();
    }


    @Bean
    RouterFunction<ServerResponse> discoveryServerRoute() {
        return GatewayRouterFunctions.route("discovery_server")
                .route(RequestPredicates.path("/eureka/web"),
                        HandlerFunctions.http("http://localhost:8761/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("discoveryServerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .filter(FilterFunctions.setPath("/"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> discoveryServerStaticRoute() {
        return GatewayRouterFunctions.route("discovery_server_static")
                .route(RequestPredicates.path("/eureka/**"),
                        HandlerFunctions.http("http://localhost:8761/"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("discoveryServerStaticCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();
    }


    @Bean
    RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("fallbackRoute",
                        request -> ServerResponse
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Unfortunately, service is unavailable at the moment, please try again later."))
                .build();
    }


}
