package site.elven.planning.boot.common.web.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.elven.planning.boot.base.web.service.property.impl.BasePropertyServiceImpl;
import site.elven.planning.boot.common.web.dal.config.CommonConfigPropertyRepository;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.boot.common.web.service.config.CommonConfigPropertyService;

@Slf4j
@Service
public class CommonConfigPropertyServiceImpl extends BasePropertyServiceImpl<CommonConfigProperty, Long, CommonConfigPropertyRepository> implements CommonConfigPropertyService {
}
