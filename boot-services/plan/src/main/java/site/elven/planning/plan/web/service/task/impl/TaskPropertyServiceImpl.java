package site.elven.planning.plan.web.service.task.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.elven.planning.boot.base.web.service.property.impl.BasePropertyServiceImpl;
import site.elven.planning.plan.web.dal.task.TaskPropertyRepository;
import site.elven.planning.plan.web.model.entity.task.TaskProperty;
import site.elven.planning.plan.web.service.task.TaskPropertyService;

@Slf4j
@Service
public class TaskPropertyServiceImpl extends BasePropertyServiceImpl<TaskProperty, Long, TaskPropertyRepository> implements TaskPropertyService {
}
