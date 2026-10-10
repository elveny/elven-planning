package site.elven.planning.experiment.web.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;

import java.util.List;

@HttpExchange(url = "/web/v1/app/task", contentType = "application/json")
public interface AppTaskRestClient {
    @GetExchange("/query-tasks")
    List<AppTaskRsp> queryTasks(@RequestParam("app_code") String appCode,
                                @RequestParam("app_secret") String secret);

}
