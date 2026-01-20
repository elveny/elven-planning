package site.elven.planning.boot.base.web.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.elven.planning.boot.base.web.dal.config.CommonConfigPropertyRepository;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.boot.base.web.service.config.CommonConfigPropertyService;
import site.elven.planning.boot.base.web.service.property.impl.BasePropertyServiceImpl;

@Slf4j
@Service
public class CommonConfigPropertyServiceImpl extends BasePropertyServiceImpl<CommonConfigProperty, Long, CommonConfigPropertyRepository> implements CommonConfigPropertyService {
}
