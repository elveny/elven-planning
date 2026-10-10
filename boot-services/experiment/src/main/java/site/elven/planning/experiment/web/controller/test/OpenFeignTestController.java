package site.elven.planning.experiment.web.controller.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.web.client.scheduler.app.AppTaskClient;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.experiment.web.client.AppTaskRestClient;

import java.util.List;

@RestController
@RequestMapping("/web/v1/test/open-feign")
public class OpenFeignTestController {

    private final AppTaskClient appTaskClient;
    private final AppTaskRestClient appTaskRestClient;

    public OpenFeignTestController(AppTaskClient appTaskClient, AppTaskRestClient appTaskRestClient) {
        this.appTaskClient = appTaskClient;
        this.appTaskRestClient = appTaskRestClient;
    }

    @RequestMapping(value = "/scheduler/query-tasks")
    public List<AppTaskRsp> queryTasks(@RequestParam("app_code") String appCode,
                                       @RequestParam("app_secret") String secret){
        return appTaskClient.queryTasks(appCode, secret);
    }

    @RequestMapping(value = "/scheduler/query-tasks-rest-client")
    public List<AppTaskRsp> queryTasksRestClient(@RequestParam("app_code") String appCode,
                                                 @RequestParam("app_secret") String secret){
        return appTaskRestClient.queryTasks(appCode, secret);
    }

}
