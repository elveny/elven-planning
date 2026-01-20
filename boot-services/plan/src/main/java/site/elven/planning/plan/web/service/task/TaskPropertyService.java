package site.elven.planning.plan.web.service.task;

import site.elven.planning.boot.base.web.service.property.BasePropertyService;
import site.elven.planning.plan.web.dal.task.TaskPropertyRepository;
import site.elven.planning.plan.web.model.entity.task.TaskProperty;

public interface TaskPropertyService extends BasePropertyService<TaskProperty, Long, TaskPropertyRepository> {
}
