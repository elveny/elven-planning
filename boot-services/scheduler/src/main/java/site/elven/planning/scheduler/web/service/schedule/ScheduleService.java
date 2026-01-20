package site.elven.planning.scheduler.web.service.schedule;

import org.quartz.SchedulerException;

import java.util.Map;

public interface ScheduleService {

    /**
     * 创建任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @param jobClassName 任务类名
     * @param jobParams 任务参数
     * @param jobDescription 任务描述
     * @param triggerMode 触发器-模式
     * @param triggerIntervalMs 触发器-间隔毫秒
     * @param triggerRepeatCount 触发器-重复次数
     * @param triggerCronExpression 触发器-cron表达式
     * @param triggerMisfire 触发器-misfire
     * @param triggerPriority 触发器-优先级
     * @param triggerDescription 触发器描述
     * @return 创建成功返回true
     * @throws SchedulerException 调度异常
     */
    boolean createTask(String groupName, String jobName, String triggerName,
                       String jobClassName, Map<String, Object> jobParams, String jobDescription,
                       String triggerMode,
                       long triggerIntervalMs, int triggerRepeatCount,
                       String triggerCronExpression,
                       String triggerMisfire, int triggerPriority, String triggerDescription) throws SchedulerException;

    /**
     * 创建simple模式的任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @param jobClassName 任务类名
     * @param jobParams 任务参数
     * @param intervalMs 触发器-间隔毫秒
     * @return 创建成功返回true
     * @throws SchedulerException 调度异常
     */
    boolean createTask(String groupName, String jobName, String triggerName,
                       String jobClassName, Map<String, Object> jobParams,
                       long intervalMs) throws SchedulerException;

    /**
     * 创建cron模式的任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @param jobClassName 任务类名
     * @param jobParams 任务参数
     * @param triggerCronExpression 触发器-cron表达式
     * @return 创建成功返回true
     * @throws SchedulerException 调度异常
     */
    boolean createTask(String groupName, String jobName, String triggerName,
                       String jobClassName, Map<String, Object> jobParams,
                       String triggerCronExpression) throws SchedulerException;

    /**
     * 删除任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @return 删除成功返回true
     * @throws SchedulerException 调度异常
     */
    boolean deleteTask(String groupName, String jobName, String triggerName) throws SchedulerException;

    /**
     * 暂停任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @return 暂停成功返回true
     * @throws SchedulerException 调度异常
     */
    boolean pauseTask(String groupName, String jobName, String triggerName) throws SchedulerException;

    /**
     * 恢复任务
     * @param groupName 组名
     * @param jobName 任务名
     * @param triggerName 触发器名
     * @return 恢复成功返回true
     * @throws SchedulerException 调度异常
     **/
    boolean resumeTask(String groupName, String jobName, String triggerName) throws SchedulerException;
}
