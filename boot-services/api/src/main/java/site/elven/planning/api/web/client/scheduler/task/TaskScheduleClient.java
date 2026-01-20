package site.elven.planning.api.web.client.scheduler.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.elven.planning.api.web.model.scheduler.response.task.TaskScheduleRsp;

@FeignClient(name = "scheduler", url = "http://127.0.0.1:8801")
public interface TaskScheduleClient {

    @RequestMapping("/planning-scheduler/web/v1/task/schedule/detail1")
    TaskScheduleRsp detail(@RequestParam("task_id") long taskId);
}
