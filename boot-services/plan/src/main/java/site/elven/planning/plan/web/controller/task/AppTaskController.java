package site.elven.planning.plan.web.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.plan.web.service.task.AppTaskService;

import java.util.List;

@RestController
@RequestMapping("/web/v1/app/task")
public class AppTaskController {
    private final AppTaskService appTaskService;

    @Autowired
    private ApplicationContext applicationContext;

    public AppTaskController(AppTaskService appTaskService) {
        this.appTaskService = appTaskService;
    }


    @GetMapping("/query-tasks")
    public List<AppTaskRsp> queryTasks(@RequestParam("app_code") String appCode,
                                       @RequestParam("app_secret") String secret) {
        return appTaskService.queryTasks(appCode, secret);
    }
}
