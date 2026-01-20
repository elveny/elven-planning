package site.elven.planning.scheduler.web.mapstruct.task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.scheduler.request.task.TaskInfoReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.scheduler.web.model.entity.task.TaskInfo;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskInfoMapStruct extends BaseMapStruct<TaskInfo, TaskInfoReq, TaskInfoRsp> {
    @Override
    @Mapping(source = "cron", target = "cronExpression")
    TaskInfo req2Entity(TaskInfoReq req);
}
