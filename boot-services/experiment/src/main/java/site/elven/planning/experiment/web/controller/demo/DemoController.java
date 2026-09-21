package site.elven.planning.experiment.web.controller.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.boot.base.common.annotation.IgnoreResponseBodyAdvice;

@RestController
@RequestMapping("/web/v1/demo")
@IgnoreResponseBodyAdvice
public class DemoController {

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }
}
