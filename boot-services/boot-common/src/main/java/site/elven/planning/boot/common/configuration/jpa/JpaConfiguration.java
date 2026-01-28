package site.elven.planning.boot.common.configuration.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("bootCommonJpaConfiguration")
@EnableJpaRepositories(basePackages = {"site.elven.planning.boot.common.web.dal"})
@EntityScan(basePackages = {"site.elven.planning.boot.common.web.model.entity"})
@Slf4j
public class JpaConfiguration {
}
