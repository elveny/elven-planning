package site.elven.planning.plan.web.dal.template;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.plan.web.model.entity.template.TemplateInfo;

@Repository
public interface TemplateInfoRepository extends BaseRepository<TemplateInfo, Long> {
}
