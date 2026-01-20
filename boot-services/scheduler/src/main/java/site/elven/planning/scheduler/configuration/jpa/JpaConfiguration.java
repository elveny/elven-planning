package site.elven.planning.scheduler.configuration.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("schedulerJpaConfiguration")
@EnableJpaRepositories(basePackages = {"site.elven.planning.scheduler.web.dal"})
@EntityScan(basePackages = {"site.elven.planning.scheduler.web.model.entity"})
public class JpaConfiguration {
}
