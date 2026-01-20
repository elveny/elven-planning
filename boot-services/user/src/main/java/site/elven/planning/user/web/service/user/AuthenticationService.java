package site.elven.planning.user.web.service.user;

import site.elven.planning.api.web.model.user.response.AuthenticationRsp;

public interface AuthenticationService {
    AuthenticationRsp check(String token);
}
