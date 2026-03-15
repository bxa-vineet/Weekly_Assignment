package com.api_gateway.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class SecurityUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Void> writeErrorResponse(ServerWebExchange exchange,
                                         HttpStatus status,
                                         String message) {
        try {
            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            byte[] bytes = objectMapper.writeValueAsString(
                    Map.of(
                            "status", status.value(),
                            "error", status.getReasonPhrase(),
                            "message", message,
                            "path", exchange.getRequest().getPath().value()
                    )
            ).getBytes(StandardCharsets.UTF_8);

            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }
}

