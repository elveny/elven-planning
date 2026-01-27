package site.elven.planning.boot.common.web.service.config;

import site.elven.planning.boot.base.web.service.property.BasePropertyService;
import site.elven.planning.boot.common.web.dal.config.CommonConfigPropertyRepository;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigProperty;

public interface CommonConfigPropertyService
        extends BasePropertyService<CommonConfigProperty, Long, CommonConfigPropertyRepository> {
}
