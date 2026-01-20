package site.elven.planning.scheduler.web.mapstruct.app;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.scheduler.request.app.AppTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.scheduler.web.model.entity.app.AppTask;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppTaskMapStruct extends BaseMapStruct<AppTask, AppTaskReq, AppTaskRsp> {
}
