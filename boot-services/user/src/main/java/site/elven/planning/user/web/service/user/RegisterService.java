package site.elven.planning.user.web.service.user;

import site.elven.planning.api.web.model.user.request.RegisterReq;
import site.elven.planning.api.web.model.user.response.UserRsp;

public interface RegisterService {
    /**
     * 注册用户
     * @param registerReq 注册请求
     * @return 用户
     */
    UserRsp createUser(RegisterReq registerReq);
}
