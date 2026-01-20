package site.elven.planning.plan.web.mapstruct.task;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.plan.request.task.TaskPropertyReq;
import site.elven.planning.api.web.model.plan.response.task.TaskPropertyRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.api.web.model.base.request.base.BaseReq;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;
import site.elven.planning.plan.web.model.entity.task.TaskProperty;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskPropertyMapStruct extends BaseMapStruct<TaskProperty, TaskPropertyReq, TaskPropertyRsp> {
}
