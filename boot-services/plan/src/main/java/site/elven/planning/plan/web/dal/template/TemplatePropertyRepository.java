package site.elven.planning.plan.web.dal.template;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.property.BasePropertyRepository;
import site.elven.planning.plan.web.model.entity.template.TemplateProperty;

import java.util.List;

@Repository
public interface TemplatePropertyRepository extends BasePropertyRepository<TemplateProperty, Long> {
}
