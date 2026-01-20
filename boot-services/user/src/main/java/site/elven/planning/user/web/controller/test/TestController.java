package site.elven.planning.user.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.boot.base.common.annotation.IgnoreResponseBodyAdvice;
import site.elven.planning.boot.base.common.exception.DefaultException;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.user.web.model.entity.user.User;

@RestController
@RequestMapping("/web/v1/test")
@IgnoreResponseBodyAdvice
public class TestController {

    @Autowired
    private ApplicationConfig applicationConfig;

    @RequestMapping(value = "/hello", produces = "application/json")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/default-ex")
    public String defaultException() {
        throw new DefaultException("9001", "test error");
    }

    @RequestMapping("/ex")
    public String exception() throws Exception {
        throw new Exception("test exception");
    }

    @RequestMapping("/user")
    public User user() {
        return new User();
    }

    @RequestMapping("/user-add")
    public User userAdd(@Validated User user) {
        return user;
    }

    @RequestMapping("/config")
    public ApplicationConfig config() {
        return applicationConfig;
    }
}
