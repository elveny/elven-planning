package site.elven.planning.user.web.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.user.request.UserReq;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.user.web.model.entity.user.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapStruct extends BaseMapStruct<User, UserReq, UserRsp> {
}
