package site.elven.planning.user.configuration.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "application.config")
@Component
public class ApplicationConfig {
    private Token token;
    private Security security;
    private Redis redis;

    @Data
    public static class Token {
        private String secret;
        private long expire;
        private String header;
        private String tokenPrefix;
    }

    @Data
    public static class Security {
        private String[] whiteList;
        private String[] manageList;
    }

    @Data
    public static class Redis {
        private String cachePrefix;
        private long cacheTtlDays;
    }

}
