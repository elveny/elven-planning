package site.elven.planning.api.web.client.scheduler.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.web.client.scheduler.app.fallback.AppTaskClientFallbackFactory;
import site.elven.planning.api.web.model.base.response.base.BaseResultRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppAddTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;

import java.util.List;

@FeignClient(name = "planning-scheduler", path = "/planning-scheduler/web/v1/app/task",
        fallbackFactory = AppTaskClientFallbackFactory.class
)
public interface AppTaskClient {
    @RequestMapping("/add-task")
    AppTaskRsp addTask(@RequestBody AppAddTaskReq req);

    @GetMapping("/query-tasks")
    List<AppTaskRsp> queryTasks(@RequestParam("app_code") String appCode,
                                @RequestParam("app_secret") String secret);

    @GetMapping("/query-task")
    AppTaskRsp queryTask(@RequestParam("app_code") String appCode,
                         @RequestParam("app_secret") String appSecret,
                         @RequestParam("task_name") String taskName);

    @PutMapping("/start-task")
    BaseResultRsp startTask(@RequestParam("app_code") String appCode,
                            @RequestParam("app_secret") String secret,
                            @RequestParam("task_name") String taskName);
    @PutMapping("/pause-task")
    BaseResultRsp pauseTask(@RequestParam("app_code") String appCode,
                            @RequestParam("app_secret") String secret,
                            @RequestParam("task_name") String taskName);

    @PutMapping("/stop-task")
    BaseResultRsp stopTask(@RequestParam("app_code") String appCode,
                           @RequestParam("app_secret") String secret,
                           @RequestParam("task_name") String taskName);

    @PutMapping("/cancel-task")
    BaseResultRsp cancelTask(@RequestParam("app_code") String appCode,
                             @RequestParam("app_secret") String secret,
                             @RequestParam("task_name") String taskName);

}
