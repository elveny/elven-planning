package site.elven.planning.user.web.controller.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.user.request.LoginReq;
import site.elven.planning.api.web.model.user.request.RegisterReq;
import site.elven.planning.api.web.model.user.request.UserReq;
import site.elven.planning.api.web.model.user.response.LoginRsp;
import site.elven.planning.api.web.model.user.response.RegisterRsp;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.user.web.holder.UserContextHolder;
import site.elven.planning.user.web.model.entity.user.User;
import site.elven.planning.user.web.service.user.LoginService;
import site.elven.planning.user.web.service.user.RegisterService;
import site.elven.planning.user.web.service.user.UserService;

@Slf4j
@RestController
@RequestMapping("/web/v1/user")
public class UserController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public LoginRsp login(@Validated(LoginReq.LoginGroup.class) @RequestBody LoginReq loginReq){
        return loginService.login(loginReq);
    }

    @RequestMapping("register")
    public RegisterRsp register(@Valid @RequestBody RegisterReq registerReq){
        UserRsp user = registerService.createUser(registerReq);
        return new RegisterRsp(user.getCode());
    }

    @PostAuthorize("@auth.user(#root)")
    @GetMapping("detail/{id}")
    public UserRsp detail(@PathVariable("id") long id){
        User currentUser = UserContextHolder.getCurrentUser();
        log.info("currentUser: {}, code: {}", currentUser.getUsername(), currentUser.getCode());
        return userService.detail(id);
    }

    @PreAuthorize("@auth.admin(#root)")
    @PostMapping("list")
    public PageRsp<UserRsp> list(@RequestBody UserReq searchReq){
        User currentUser = UserContextHolder.getCurrentUser();
        log.info("currentUser: {}, code: {}", currentUser.getUsername(), currentUser.getCode());
        return userService.list(searchReq);
    }

}
