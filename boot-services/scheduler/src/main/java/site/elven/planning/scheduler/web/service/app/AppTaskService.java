package site.elven.planning.scheduler.web.service.app;

import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.scheduler.request.app.AppAddTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.scheduler.web.dal.app.AppTaskRepository;
import site.elven.planning.scheduler.web.model.entity.app.AppTask;

import java.util.List;

public interface AppTaskService extends IService<AppTask, Long, AppTaskRepository> {
    AppRegistryRsp checkApp(String appCode, String appSecret);
    AppTaskRsp addTask(AppAddTaskReq req);
    List<AppTaskRsp> queryTask(String appCode, String appSecret);
    AppTaskRsp queryTask(String appCode, String appSecret, String taskName);
    int updateStatus(String appCode, String appSecret, String taskName, TaskStatus status);

}
