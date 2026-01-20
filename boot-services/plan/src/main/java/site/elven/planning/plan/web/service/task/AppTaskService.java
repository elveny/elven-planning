package site.elven.planning.plan.web.service.task;

import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;

import java.util.List;

public interface AppTaskService {
    List<AppTaskRsp> queryTasks(String appCode, String secret);
}
