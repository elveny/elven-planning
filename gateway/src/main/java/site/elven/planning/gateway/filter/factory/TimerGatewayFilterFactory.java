package site.elven.planning.gateway.filter.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Slf4j
@Component
public class TimerGatewayFilterFactory extends AbstractGatewayFilterFactory<TimerGatewayFilterFactory.Config> {

    public TimerGatewayFilterFactory() {
        super(Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre 阶段
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            long start = System.currentTimeMillis();
            String path = exchange.getRequest().getURI().getPath();
            log.info("[Timer] 开始请求: {}", path);

            // 打印 Metadata
            printMetadata(exchange, chain);

            // chain.filter 执行后续过滤器和代理转发
            // .then(...) 中的逻辑在响应返回后执行，即 Post 阶段
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                long cost = System.currentTimeMillis() - start;
                log.info("[Timer] 请求结束: {}，耗时 {} ms", path, cost);
            }));
        };

    }

    public Mono<Void> printMetadata(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取 Metadata
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return chain.filter(exchange);
        }

        Map<String, Object> metadata = route.getMetadata();
        log.info("[Metadata] Metadata: {}", metadata);
        Object optionName = metadata.get("optionName");
        log.info("[Metadata] optionName: {}", optionName);
        Object compositeObject = metadata.get("compositeObject");
        log.info("[Metadata] compositeObject: {}", compositeObject);
        return null;
    }

    @Setter
    @Getter
    public static class Config {
        private boolean enabled = true;

    }

}
