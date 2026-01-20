package site.elven.planning.user.configuration.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApplicationConfig.class, ThreadPoolProperties.class})
public class WebConfig {
}
