package site.elven.planning.boot.base.web.service.config;

import site.elven.planning.boot.base.web.dal.config.CommonConfigPropertyRepository;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.boot.base.web.service.property.BasePropertyService;

public interface CommonConfigPropertyService
        extends BasePropertyService<CommonConfigProperty, Long, CommonConfigPropertyRepository> {
}
