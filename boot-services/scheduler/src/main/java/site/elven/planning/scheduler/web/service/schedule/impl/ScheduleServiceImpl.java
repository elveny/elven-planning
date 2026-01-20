package site.elven.planning.scheduler.web.service.schedule.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.elven.planning.scheduler.common.enums.MisfireInstruction;
import site.elven.planning.scheduler.common.enums.TriggerMode;
import site.elven.planning.scheduler.web.service.schedule.ScheduleService;
import site.elven.planning.scheduler.web.service.task.TaskInfoService;

import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public boolean createTask(String groupName, String jobName, String triggerName,
                              String jobClassName, Map<String, Object> jobParams, String jobDescription,
                              String triggerMode,
                              long triggerIntervalMs, int triggerRepeatCount,
                              String triggerCronExpression,
                              String triggerMisfire, int triggerPriority, String triggerDescription) throws SchedulerException {
        // 创建 jobKey
        JobKey jobKey = new JobKey(jobName, groupName);
        // 判断 job 是否存在
        if (scheduler.checkExists(jobKey)){
            throw new SchedulerException("job already exists");
        }

        // 创建 triggerKey
        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        // 判断 trigger 是否存在
        if (scheduler.checkExists(triggerKey)) {
            throw new SchedulerException("trigger already exists");
        }

        // 创建 jobDetail 和 trigger
        JobDetail jobDetail = this.createJob(groupName, jobName, jobClassName, jobParams, jobDescription);
        Trigger trigger = this.createTrigger(groupName, triggerName,
                triggerMode,
                triggerIntervalMs, triggerRepeatCount,
                triggerCronExpression,
                triggerMisfire, triggerPriority, triggerDescription);
        scheduler.scheduleJob(jobDetail, trigger);
        return true;
    }

    @Override
    public boolean createTask(String groupName, String jobName, String triggerName, String jobClassName, Map<String, Object> jobParams, long intervalMs) throws SchedulerException {
        return createTask(groupName, jobName, triggerName,
                jobClassName, jobParams, null ,
                TriggerMode.simple.name(),
                intervalMs, -1,
                null,
                null, 0, null);
    }

    @Override
    public boolean createTask(String groupName, String jobName, String triggerName, String jobClassName, Map<String, Object> jobParams, String triggerCronExpression) throws SchedulerException {
        return createTask(groupName, jobName, triggerName,
                jobClassName, jobParams, null ,
                TriggerMode.cron.name(),
                0, -1,
                triggerCronExpression,
                null, 0, null);
    }

    @Override
    public boolean deleteTask(String groupName, String jobName, String triggerName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, groupName);
        if (scheduler.checkExists(jobKey)) {
            return scheduler.deleteJob(jobKey);
        }
        return true;
    }

    @Override
    public boolean pauseTask(String groupName, String jobName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            return true;
        }
        return false;
    }

    @Override
    public boolean resumeTask(String groupName, String jobName, String triggerName) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.resumeTrigger(triggerKey);
            return true;
        }
        return false;
    }

    /**
     * 创建 jobDetail
     *
     * @param groupName 组名
     * @param jobName  任务名
     * @param jobClassName 任务类名
     * @param jobParams 任务参数
     * @param description 任务描述
     * @return 任务详情
     * @throws SchedulerException 异常
     */
    private JobDetail createJob(String groupName, String jobName, String jobClassName, Map<String, Object> jobParams, String description) throws SchedulerException {
        // 创建 jobKey
        JobKey jobKey = new JobKey(jobName, groupName);
        // 根据 jobClassName 获取 jobClass
        Class<?> jobClass;
        try {
            jobClass = Class.forName(jobClassName);
            // 注意 jobClass 必须是 org.quartz.Job 的实现类，否则会抛出异常
            if (!Job.class.isAssignableFrom(jobClass)) {
                throw new IllegalArgumentException("The specified class does not implement org.quartz.Job");
            }
        } catch (ClassNotFoundException e) {
            throw new SchedulerException(e);
        }

        @SuppressWarnings("unchecked")
        Class<? extends Job> jobClassCast = (Class<? extends Job>) jobClass;

        // 创建 jobDetail
        JobDetail jobDetail = JobBuilder.newJob(jobClassCast)
                .withIdentity(jobKey)
                .withDescription(description)
                .build();

        // 设置 jobDetail 的参数
        if(jobParams != null){
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.putAll(jobParams);
        }
        return jobDetail;
    }

    /**
     * 创建 trigger
     * @param groupName 组名
     * @param triggerName 触发器名
     * @param triggerMode 触发器-模式
     * @param intervalMs 触发器-间隔毫秒
     * @param triggerRepeatCount 触发器-重复次数
     * @param cronExpression 触发器-cron表达式
     * @param misfire 触发器-misfire
     * @param priority 触发器-优先级
     * @param description 触发器-描述
     * @return 触发器
     */
    private Trigger createTrigger(String groupName, String triggerName,
                                  String triggerMode,
                                  long intervalMs, int triggerRepeatCount,
                                  String cronExpression,
                                  String misfire,
                                  int priority,
                                  String description) {
        // 创建 triggerKey
        TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

        // 创建 scheduleBuilder：根据不同的 triggerMode 来创建 scheduleBuilder
        ScheduleBuilder<? extends Trigger> scheduleBuilder = switch (TriggerMode.valueOf(triggerMode)) {
            case simple -> withSimpleSchedule(intervalMs, triggerRepeatCount, misfire);
            case cron -> withCronSchedule(cronExpression, misfire);
        };

        // 创建 trigger
        TriggerBuilder<? extends Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withPriority(priority)
                .withIdentity(triggerKey)
                .withSchedule(scheduleBuilder)
                .withDescription(description);

        // 设置触发器优先级
        if(priority >= 0){
            triggerBuilder.withPriority(priority);
        }
        return triggerBuilder.build();
    }

    /**
     * 构建 simple 模式的 scheduleBuilder
     * @param intervalMs 间隔毫秒
     * @param repeatCount 重复次数
     * @param misfire 丢失触发处理方式
     * @return scheduleBuilder
     */
    private SimpleScheduleBuilder withSimpleSchedule(long intervalMs, int repeatCount, String misfire){
        // 构建 scheduleBuilder
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(intervalMs);

        // 设置重复次数：如果小于0，则无限重复
        if(repeatCount <= 0){
            scheduleBuilder.repeatForever();
        }
        else {
            scheduleBuilder.withRepeatCount(repeatCount);
        }

        // 设置 丢失触发情况的处理方式
        if (misfire != null){
            switch (MisfireInstruction.valueOf(misfire)) {
                case ignore -> scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                case fire_now -> scheduleBuilder.withMisfireHandlingInstructionFireNow();
                case next_with_existing_count ->
                        scheduleBuilder.withMisfireHandlingInstructionNextWithExistingCount();
                case now_with_existing_count -> scheduleBuilder.withMisfireHandlingInstructionNowWithExistingCount();
                case next_with_remaining_count ->
                        scheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                case now_with_remaining_count ->
                        scheduleBuilder.withMisfireHandlingInstructionNowWithRemainingCount();
            }
        }

        return scheduleBuilder;

    }

    /**
     * 构建 cron 模式的 scheduleBuilder
     * @param cronExpression cron表达式
     * @param misfire 丢失触发处理方式
     * @return scheduleBuilder
     */
    private CronScheduleBuilder withCronSchedule(String cronExpression, String misfire){
        // 构建 scheduleBuilder
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        // 设置 丢失触发情况的处理方式
        if (misfire != null){
            switch (MisfireInstruction.valueOf(misfire)) {
                case ignore -> scheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                case do_nothing -> scheduleBuilder.withMisfireHandlingInstructionDoNothing();
                case fire_once_now -> scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            }
        }

        return scheduleBuilder;
    }

}
