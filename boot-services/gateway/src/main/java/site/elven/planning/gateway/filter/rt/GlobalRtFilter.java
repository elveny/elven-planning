package site.elven.planning.gateway.filter.rt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalRtFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String uri = request.getURI().toString();
        String path = request.getPath().toString();
        long start = System.currentTimeMillis();

        return chain.filter(exchange)
                .doFinally(result -> {
                    long end = System.currentTimeMillis();
                    log.info("uri: {}, path: {}, time: {}", uri, path, end - start);
                });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
