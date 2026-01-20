package site.elven.planning.plan.web.service.template.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.elven.planning.boot.base.web.service.property.impl.BasePropertyServiceImpl;
import site.elven.planning.plan.web.dal.template.TemplatePropertyRepository;
import site.elven.planning.plan.web.model.entity.template.TemplateProperty;
import site.elven.planning.plan.web.service.template.TemplatePropertyService;

@Slf4j
@Service
public class TemplatePropertyServiceImpl extends BasePropertyServiceImpl<TemplateProperty, Long, TemplatePropertyRepository> implements TemplatePropertyService {
}
