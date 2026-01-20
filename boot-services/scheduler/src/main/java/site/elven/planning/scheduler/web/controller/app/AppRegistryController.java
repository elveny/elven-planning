package site.elven.planning.scheduler.web.controller.app;

import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.web.model.base.response.base.BaseResultRsp;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppRegistryReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.scheduler.web.service.app.AppRegisterService;

@RestController
@RequestMapping("/web/v1/app/registry")
public class AppRegistryController {
    private final AppRegisterService appRegisterService;

    public AppRegistryController(AppRegisterService appRegisterService) {
        this.appRegisterService = appRegisterService;
    }

    @RequestMapping("/register")
    public AppRegistryRsp register(@RequestBody AppRegistryReq req) {
        return appRegisterService.register(req);
    }

    @GetMapping("/detail/{id}")
    public AppRegistryRsp detail(@PathVariable("id") long id) {
        return appRegisterService.detail(id);
    }

    @GetMapping("/detail")
    public AppRegistryRsp detail(@RequestParam("code") String code, @RequestParam("secret") String secret) {
        return appRegisterService.detail(code, secret);
    }

    @RequestMapping("/list")
    public PageRsp<AppRegistryRsp> list(@RequestBody AppRegistryReq req) {
        return appRegisterService.list(req);
    }

    @PutMapping("/disable/{id}")
    public BaseResultRsp disable(@PathVariable("id") long id) {
        return new BaseResultRsp(appRegisterService.disable(id));
    }

    @PutMapping("/enable/{id}")
    public BaseResultRsp enable(@PathVariable("id") long id) {
        return new BaseResultRsp(appRegisterService.enable(id));
    }
}
