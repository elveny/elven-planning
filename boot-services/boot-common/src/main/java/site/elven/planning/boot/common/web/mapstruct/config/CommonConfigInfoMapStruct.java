package site.elven.planning.boot.common.web.mapstruct.config;

import org.mapstruct.Mapper;
import site.elven.planning.api.web.model.base.request.config.CommonConfigInfoReq;
import site.elven.planning.api.web.model.base.response.config.CommonConfigInfoRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigInfo;

@Mapper(componentModel = "spring")
public interface CommonConfigInfoMapStruct extends BaseMapStruct<CommonConfigInfo, CommonConfigInfoReq, CommonConfigInfoRsp> {
}
