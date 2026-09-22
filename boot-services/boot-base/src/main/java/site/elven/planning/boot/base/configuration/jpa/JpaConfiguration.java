package site.elven.planning.boot.base.configuration.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("bootBaseJpaConfiguration")
@EnableJpaRepositories(basePackages = {"site.elven.planning.boot.base.web.dal"})
@EntityScan(basePackages = {"site.elven.planning.boot.base.web.model.entity"})
@Slf4j
public class JpaConfiguration {
}
