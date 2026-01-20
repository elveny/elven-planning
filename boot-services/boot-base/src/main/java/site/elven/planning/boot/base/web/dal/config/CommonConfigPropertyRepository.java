package site.elven.planning.boot.base.web.dal.config;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.property.BasePropertyRepository;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigProperty;

@Repository
public interface CommonConfigPropertyRepository extends BasePropertyRepository<CommonConfigProperty, Long> {
}
