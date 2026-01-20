package site.elven.planning.user.web.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.web.model.user.response.AuthenticationRsp;
import site.elven.planning.user.web.service.user.AuthenticationService;

@Slf4j
@RestController
@RequestMapping("/web/v1/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("check")
    public AuthenticationRsp check(String token) {
        return authenticationService.check(token);
    }
}
