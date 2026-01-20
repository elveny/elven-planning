package site.elven.planning.plan.web.dal.task;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.property.BasePropertyRepository;
import site.elven.planning.plan.web.model.entity.task.TaskProperty;

@Repository
public interface TaskPropertyRepository extends BasePropertyRepository<TaskProperty, Long> {
}
