package site.elven.planning.plan.configuration.jpa;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("planJpaConfiguration")
@EnableJpaRepositories(basePackages = {"site.elven.planning.plan.web.dal"})
@EntityScan(basePackages = {"site.elven.planning.plan.web.model.entity"})
public class JpaConfiguration {
}
