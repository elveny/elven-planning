package site.elven.planning.boot.common.web.mapstruct.config;

import org.mapstruct.Mapper;
import site.elven.planning.api.web.model.base.request.config.CommonConfigPropertyReq;
import site.elven.planning.api.web.model.base.response.config.CommonConfigPropertyRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigProperty;

@Mapper(componentModel = "spring")
public interface CommonConfigPropertyMapStruct extends BaseMapStruct<CommonConfigProperty, CommonConfigPropertyReq, CommonConfigPropertyRsp> {
}
