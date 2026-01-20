package site.elven.planning.scheduler.web.service.app.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.scheduler.request.app.AppAddTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.scheduler.common.exception.ExceptionCode;
import site.elven.planning.scheduler.common.exception.SchedulerException;
import site.elven.planning.scheduler.web.dal.app.AppTaskRepository;
import site.elven.planning.scheduler.web.mapstruct.app.AppTaskMapStruct;
import site.elven.planning.scheduler.web.model.entity.app.AppTask;
import site.elven.planning.scheduler.web.service.app.AppRegisterService;
import site.elven.planning.scheduler.web.service.app.AppTaskService;

import java.util.List;

@Slf4j
@Service
public class AppTaskServiceImpl extends ServiceImpl<AppTask, Long, AppTaskRepository> implements AppTaskService {
    private final AppRegisterService appRegisterService;

    private final AppTaskMapStruct appTaskMapStruct;

    public AppTaskServiceImpl(AppRegisterService appRegisterService, AppTaskMapStruct appTaskMapStruct) {
        this.appRegisterService = appRegisterService;
        this.appTaskMapStruct = appTaskMapStruct;
    }

    public AppRegistryRsp checkApp(String appCode, String appSecret) {
        // 参数校验
        if(!StringUtils.hasText(appCode)){
            String message = messageManager.getMessage(ExceptionCode.REQUEST_PARAM_ERROR.getMessageCode(), "appCode");
            throw new SchedulerException(ExceptionCode.REQUEST_PARAM_ERROR.getCode(), message);
        }
        if(!StringUtils.hasText(appSecret)){
            String message = messageManager.getMessage(ExceptionCode.REQUEST_PARAM_ERROR.getMessageCode(), "appSecret");
            throw new SchedulerException(ExceptionCode.REQUEST_PARAM_ERROR.getCode(), message);
        }

        // 应用校验
        AppRegistryRsp appRegistry = appRegisterService.detail(appCode, appSecret);
        if (appRegistry == null){
            String message = messageManager.getMessage(ExceptionCode.APP_NOT_EXIST.getMessageCode(), appCode);
            throw new SchedulerException(ExceptionCode.APP_NOT_EXIST.getCode(), message);
        }
        return appRegistry;
    }

    @Override
    public AppTaskRsp addTask(AppAddTaskReq req) {
        String appCode = req.getAppCode();
        String appSecret = req.getAppSecret();

        AppRegistryRsp appRegistry = checkApp(appCode, appSecret);

        AppTask appTask = appTaskMapStruct.req2Entity(req);
        // 如果没有指定任务组，则默认为应用代码
        if(!StringUtils.hasText(appTask.getGroupName())){
            appTask.setGroupName(appRegistry.getGroupName());
        }
        // 任务状态默认为初始化
        if(appTask.getTaskStatus() == null){
            appTask.setTaskStatus(TaskStatus.init);
        }
        appTask = save(appTask);

        if (appTask.getTaskStatus() == TaskStatus.running){
            log.info("[app_task] 开始启动任务...");
            log.info("[app_task] ...启动任务成功");
        }

        return appTaskMapStruct.entity2Rsp(appTask);
    }

    @Override
    public List<AppTaskRsp> queryTask(String appCode, String appSecret) {
        // 首先校验应用
        checkApp(appCode, appSecret);
        log.info("[app_task] 查询应用任务列表...");

        AppTask search = new AppTask();
        search.setAppCode(appCode);
        List<AppTask> appTasks = findAll(search);
        return appTaskMapStruct.entity2Rsp(appTasks);
    }

    @Override
    public AppTaskRsp queryTask(String appCode, String appSecret, String taskName) {
        // 首先校验应用
        checkApp(appCode, appSecret);

        log.info("[app_task] 获取指定任务...");
        AppTask search = new AppTask();
        search.setAppCode(appCode);
        search.setTaskName(taskName);
        AppTask appTask = findOne(search);
        return appTaskMapStruct.entity2Rsp(appTask);
    }

    @Override
    public int updateStatus(String appCode, String appSecret, String taskName, TaskStatus status) {
        // 首先校验应用
        checkApp(appCode, appSecret);
        log.info("[app_task] 更新任务状态...");

        AppTask search = new AppTask();
        search.setAppCode(appCode);
        search.setTaskName(taskName);
        AppTask appTask = findOne(search);
        if (appTask != null){
            appTask.setTaskStatus(status);
            update(appTask);
            log.info("[app_task] 更新任务状态成功");
            return 1;
        }
        return 0;
    }
}
