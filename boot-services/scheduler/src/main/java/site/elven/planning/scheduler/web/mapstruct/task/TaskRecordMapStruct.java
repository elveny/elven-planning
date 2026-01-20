package site.elven.planning.scheduler.web.mapstruct.task;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.scheduler.request.task.TaskRecordReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.scheduler.web.model.entity.task.TaskRecord;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskRecordMapStruct extends BaseMapStruct<TaskRecord, TaskRecordReq, TaskRecordRsp> {
}
