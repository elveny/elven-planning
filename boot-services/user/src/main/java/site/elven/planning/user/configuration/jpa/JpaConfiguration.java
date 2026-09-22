package site.elven.planning.user.configuration.jpa;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("userJpaConfiguration")
@EnableJpaRepositories(basePackages = {"site.elven.planning.user.web.dal"})
@EntityScan(basePackages = {"site.elven.planning.user.web.model.entity"})
public class JpaConfiguration {
}
