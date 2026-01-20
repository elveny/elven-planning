package site.elven.planning.api.web.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import site.elven.planning.api.web.model.user.response.AuthenticationRsp;

@FeignClient(name = "planning-user", path = "/planning-user/web/v1/authentication")
public interface AuthenticationClient {
    @RequestMapping("check")
    AuthenticationRsp check(String token);
}
