package site.elven.planning.user.configuration.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.config.thread.pool")
public class ThreadPoolProperties {
    private int coreSize;
    private int maxSize;
    private int queueCapacity;
    private int keepAliveSeconds;
}
