package site.elven.planning.scheduler.web.controller.app;

import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.base.response.base.BaseResultRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppAddTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.scheduler.web.service.app.AppTaskService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/web/v1/app/task")
public class AppTaskController {
    private final AppTaskService appTaskService;

    public AppTaskController(AppTaskService appTaskService) {
        this.appTaskService = appTaskService;
    }

    @RequestMapping("/add-task")
    public AppTaskRsp addTask(@RequestBody AppAddTaskReq req) {
        return appTaskService.addTask(req);
    }

    @GetMapping("/query-tasks")
    public List<AppTaskRsp> queryTasks(@RequestParam("app_code") String appCode,
                                       @RequestParam("app_secret") String secret) throws InterruptedException {
        List<AppTaskRsp> appTaskRsps = appTaskService.queryTask(appCode, secret);
//        int i = 1/0;
        TimeUnit.SECONDS.sleep(1);
        return appTaskRsps;
    }

    @GetMapping("/query-task")
    public AppTaskRsp queryTask(@RequestParam("app_code") String appCode,
                                @RequestParam("app_secret") String appSecret,
                                @RequestParam("task_name") String taskName) {
        return appTaskService.queryTask(appCode, appSecret, taskName);
    }

    @PutMapping("/start-task")
    public BaseResultRsp startTask(@RequestParam("app_code") String appCode,
                                 @RequestParam("app_secret") String secret,
                                 @RequestParam("task_name") String taskName
    ) {
        return new BaseResultRsp(appTaskService.updateStatus(appCode, secret, taskName, TaskStatus.running) == 1);
    }

    @PutMapping("/pause-task")
    public BaseResultRsp pauseTask(@RequestParam("app_code") String appCode,
                                   @RequestParam("app_secret") String secret,
                                   @RequestParam("task_name") String taskName
    ) {
        return new BaseResultRsp(appTaskService.updateStatus(appCode, secret, taskName, TaskStatus.paused) == 1);
    }

    @PutMapping("/stop-task")
    public BaseResultRsp stopTask(@RequestParam("app_code") String appCode,
                                   @RequestParam("app_secret") String secret,
                                   @RequestParam("task_name") String taskName
    ) {
        return new BaseResultRsp(appTaskService.updateStatus(appCode, secret, taskName, TaskStatus.stopped) == 1);
    }

    @PutMapping("/cancel-task")
    public BaseResultRsp cancelTask(@RequestParam("app_code") String appCode,
                                  @RequestParam("app_secret") String secret,
                                  @RequestParam("task_name") String taskName
    ) {
        return new BaseResultRsp(appTaskService.updateStatus(appCode, secret, taskName, TaskStatus.canceled) == 1);
    }
}
