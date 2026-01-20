package site.elven.planning.scheduler.web.mapstruct.app;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.scheduler.request.app.AppRegistryReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.scheduler.web.model.entity.app.AppRegistry;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppRegistryMapStruct extends BaseMapStruct<AppRegistry, AppRegistryReq, AppRegistryRsp> {
}
