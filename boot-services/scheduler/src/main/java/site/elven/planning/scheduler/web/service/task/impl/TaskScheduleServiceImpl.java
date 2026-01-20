package site.elven.planning.scheduler.web.service.task.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;
import site.elven.planning.scheduler.common.constant.TaskParamName;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.scheduler.web.mapstruct.task.TaskInfoMapStruct;
import site.elven.planning.scheduler.web.model.entity.task.TaskInfo;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;
import site.elven.planning.scheduler.web.service.schedule.ScheduleService;
import site.elven.planning.scheduler.web.service.task.TaskInfoService;
import site.elven.planning.scheduler.web.service.task.TaskScheduleService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TaskScheduleServiceImpl implements TaskScheduleService {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private TaskInfoMapStruct taskInfoMapStruct;

    @Transactional
    @Override
    public TaskInfoRsp createTask(TaskScheduleReq taskScheduleReq) throws SchedulerException {
        String taskName = taskScheduleReq.getTaskName();
        String taskDesc = taskScheduleReq.getTaskDesc();
        TaskStatus taskStatus = taskScheduleReq.getTaskStatus();
        if (taskStatus == null){
            taskStatus = TaskStatus.init;
            taskScheduleReq.setTaskStatus(taskStatus);
        }
        Map<String, Object> jobParams = taskScheduleReq.getJobParams();
        jobParams.put(TaskParamName.TASK_NAME, taskName);
        jobParams.put(TaskParamName.TASK_DESC, taskDesc);

        TaskInfo taskInfo = taskInfoMapStruct.req2Entity(taskScheduleReq);
        taskInfo = taskInfoService.save(taskInfo, jobParams);

        if (TaskStatus.running == taskStatus) {
            jobParams.put(TaskParamName.TASK_ID, taskInfo.getId());

            boolean created = createScheduleTask(taskInfo, jobParams);
            if (!created){
                taskInfo.setTaskStatus(TaskStatus.init);
                taskInfoService.save(taskInfo);
            }
        }

        return taskInfoMapStruct.entity2Rsp(taskInfo);
    }

    @Override
    public boolean startTask(long taskId) {
        // 查询任务信息
        TaskInfo taskInfo = taskInfoService.findById(taskId);
        if (taskInfo != null){
            try {
                // 判断任务状态
                TaskStatus taskStatus = taskInfo.getTaskStatus();
                if (!(TaskStatus.init == taskStatus)){
                    log.warn("[task] 任务不处于init状态，不能启动 [taskStatus={}]", taskStatus);
                    return false;
                }

                // 判断任务是否启用
                if (!taskInfo.getEnabled()){
                    log.warn("[task] 任务处于disabled状态，不能启动");
                    return false;
                }

                Map<String, Object> taskProperties = taskInfoService.getTaskProperties(taskId);
                if (taskProperties == null){
                    taskProperties = new HashMap<>();
                }

                // 启动任务
                boolean created = createScheduleTask(taskInfo, taskProperties);
                if (created){
                    taskInfo.setTaskStatus(TaskStatus.running);
                    log.info("[schedule] 启动任务成功");
                }
                else {
                    taskInfo.setTaskStatus(TaskStatus.error);
                    log.error("[schedule] 启动任务失败");
                }

                // 更新任务状态
                taskInfoService.save(taskInfo);
                log.info("[task] 更新任务状态成功");
                return true;
            } catch (SchedulerException e) {
                log.error("[task] 启动任务失败", e);
            }
        }
        return false;
    }

    private boolean createScheduleTask(TaskInfo taskInfo, Map<String, Object> jobParams) throws SchedulerException {
        long taskId = taskInfo.getId();
        String groupName = taskInfo.getGroupName();
        String jobName = taskInfo.getJobName();
        String triggerName = taskInfo.getTriggerName();
        String jobClassName = taskInfo.getJobClassName();
        String cron = taskInfo.getCronExpression();

        jobParams.put(TaskParamName.TASK_ID, taskId);
        // 启动任务
        return scheduleService.createTask(groupName, jobName, triggerName, jobClassName, jobParams, cron);
    }

    @Override
    public boolean pauseTask(long taskId) {
        // 查询任务信息
        TaskInfo taskInfo = taskInfoService.findById(taskId);
        if (taskInfo != null){
            try {
                // 判断任务状态
                TaskStatus taskStatus = taskInfo.getTaskStatus();
                if (!(TaskStatus.running == taskStatus)){
                    log.warn("[task] 任务不处于running状态，不能暂停 [taskStatus={}]", taskStatus);
                    return false;
                }

                // 暂停任务
                boolean pauseTask = scheduleService.pauseTask(taskInfo.getGroupName(), taskInfo.getJobName(), taskInfo.getTriggerName());
                if (!pauseTask){
                    log.warn("[schedule] 暂停任务失败");
                    return false;
                }
                log.info("[schedule] 暂停任务成功");

                // 更新任务状态
                taskInfo.setTaskStatus(TaskStatus.paused);
                taskInfoService.save(taskInfo);
                log.info("[task] 更新任务状态成功");
                return true;
            } catch (SchedulerException e) {
                log.error("[task] 暂停任务失败", e);
            }
        }
        return false;
    }

    @Override
    public boolean resumeTask(long taskId) {
        // 查询任务信息
        TaskInfo taskInfo = taskInfoService.findById(taskId);
        if (taskInfo != null){
            try {
                // 判断任务状态
                TaskStatus taskStatus = taskInfo.getTaskStatus();
                if (!(TaskStatus.paused == taskStatus)){
                    log.warn("[task] 任务不处于paused状态，不能恢复 [taskStatus={}]", taskStatus);
                    return false;
                }

                // 恢复任务
                boolean resumeTask = scheduleService.resumeTask(taskInfo.getGroupName(), taskInfo.getJobName(), taskInfo.getTriggerName());
                if (!resumeTask){
                    log.warn("[schedule] 恢复任务失败");
                    return false;
                }
                log.info("[schedule] 恢复任务成功");

                // 更新任务状态
                taskInfo.setTaskStatus(TaskStatus.running);
                taskInfoService.save(taskInfo);
                log.info("[task] 更新任务状态成功");
                return true;
            } catch (SchedulerException e) {
                log.error("[task] 恢复任务失败", e);
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteTask(long taskId) {
        // 查询任务信息
        TaskInfo taskInfo = taskInfoService.findById(taskId);
        if (taskInfo != null){
            try {
                // 判断任务状态
                TaskStatus taskStatus = taskInfo.getTaskStatus();
                if (!(TaskStatus.init == taskStatus)
                        && !(TaskStatus.paused == taskStatus)
                        && !(TaskStatus.stopped == taskStatus)) {
                    log.warn("[task] 任务不处于init|paused|stopped状态，不能删除 [taskStatus={}]", taskStatus);
                    return false;
                }

                // 删除任务
                boolean deleteTask = scheduleService.deleteTask(taskInfo.getGroupName(), taskInfo.getJobName(), taskInfo.getTriggerName());
                if (!deleteTask){
                    log.warn("[schedule] 删除任务失败");
                    return false;
                }
                log.info("[schedule] 删除任务成功");

                // 删除任务记录
                taskInfoService.logicDelete(taskInfo);
                log.info("[task] 删除任务记录成功");
                return true;
            } catch (SchedulerException e) {
                log.error("[task] 删除任务失败", e);
            }
        }
        return false;
    }
}
