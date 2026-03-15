package com.api_gateway.filter;
import com.api_gateway.security.JwtTokenProvider;
import com.api_gateway.security.JwtTokenValidator;
import com.api_gateway.util.SecurityUtil;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private final JwtTokenValidator routeValidator;
    private final JwtTokenProvider jwtService;
    private final SecurityUtil securityUtil;

    public AuthenticationFilter(JwtTokenValidator routeValidator,
                                JwtTokenProvider jwtService,
                                SecurityUtil securityUtil) {
        this.routeValidator = routeValidator;
        this.jwtService = jwtService;
        this.securityUtil = securityUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (!routeValidator.isSecured.test(request)) {
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return securityUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header is missing"
            );
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return securityUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Authorization header"
            );
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            return securityUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired JWT token"
            );
        }

        String username = jwtService.extractUsername(token);
        String roles = String.join(",", jwtService.extractRoles(token));

        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-Authenticated-User", username)
                .header("X-Authenticated-Roles", roles)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

