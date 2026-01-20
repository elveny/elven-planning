package site.elven.planning.scheduler.web.service.task;

import org.quartz.SchedulerException;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;

public interface TaskScheduleService {

    /**
     * 创建任务
     * @param taskScheduleReq 请求参数
     * @return taskInfo
     * @throws SchedulerException 调度异常
     */
    TaskInfoRsp createTask(TaskScheduleReq taskScheduleReq) throws SchedulerException;

    /**
     * 启动任务
     * @param taskId 任务ID
     * @return 启动结果
     */
    boolean startTask(long taskId);

    /**
     * 暂停任务
     * @param taskId 任务ID
     * @return 暂停结果
     */
    boolean pauseTask(long taskId);

    /**
     * 恢复任务
     * @param taskId 任务ID
     * @return 恢复结果
     */
    boolean resumeTask(long taskId);

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return 删除结果
     */
    boolean deleteTask(long taskId);
}
