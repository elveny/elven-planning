package site.elven.planning.plan.web.mapstruct.task;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.plan.web.model.entity.task.TaskInfo;
import site.elven.planning.api.web.model.plan.request.task.TaskInfoReq;
import site.elven.planning.api.web.model.plan.response.task.TaskDetailRsp;
import site.elven.planning.api.web.model.plan.response.task.TaskInfoRsp;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskInfoMapStruct extends BaseMapStruct<TaskInfo, TaskInfoReq, TaskInfoRsp> {
    TaskDetailRsp toDetail (TaskInfoRsp taskInfoRsp);
}
