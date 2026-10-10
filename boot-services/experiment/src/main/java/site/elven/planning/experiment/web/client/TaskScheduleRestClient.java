package site.elven.planning.experiment.web.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import site.elven.planning.api.web.model.scheduler.response.task.TaskScheduleRsp;

@HttpExchange(url = "/web/v1/app/task", contentType = "application/json")
public interface TaskScheduleRestClient {
    @GetExchange("/schedule/detail1")
    TaskScheduleRsp detail(@RequestParam("task_id") long taskId);
}
