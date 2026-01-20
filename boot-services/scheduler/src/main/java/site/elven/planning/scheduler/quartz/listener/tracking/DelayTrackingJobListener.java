package site.elven.planning.scheduler.quartz.listener.tracking;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.elven.planning.scheduler.quartz.listener.base.BaseJobListener;
import site.elven.planning.scheduler.web.model.entity.task.TaskRecord;
import site.elven.planning.scheduler.web.service.task.TaskRecordService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class DelayTrackingJobListener extends BaseJobListener {
    @Autowired
    private TaskRecordService taskRecordService;
    @Override
    public String getName() {
        return "delay-tracking-job-listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        long jobRunTime = context.getJobRunTime();
        long now = System.currentTimeMillis();
        long fireTime = context.getFireTime().getTime();
        long scheduledFireTime = context.getScheduledFireTime().getTime();
        long previousFireTime = context.getPreviousFireTime() == null ? 0 : context.getPreviousFireTime().getTime();
        long nextFireTime = context.getNextFireTime().getTime();

        log.info("jobToBeExecuted: fireTime={}, scheduledFireTime={}, previousFireTime={}, nextFireTime={}, now={}, jobRunTime={}, delayTime={}",
                fireTime, scheduledFireTime, previousFireTime, nextFireTime, now, jobRunTime, (fireTime - scheduledFireTime));
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
        recordTracking(context, true, false, "");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        long now = System.currentTimeMillis();
        long jobRunTime = context.getJobRunTime();
        log.info("jobWasExecuted: now={}, jobRunTime={}", now, jobRunTime);
        if(jobException != null){
            recordTracking(context, false, true, jobException.getMessage());
            return;
        }
        recordTracking(context, false, false, "");
    }

    private void recordTracking(JobExecutionContext context, boolean vetoed, boolean isError, String errorMessage){

        TaskRecord taskRecord = new TaskRecord();

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        // taskId: 任务ID
        long taskId = 0;
        if(jobDataMap.containsKey("taskId")){
            taskId = jobDataMap.getLong("taskId");
        }

        // scheduledFireTime：任务调度时间
        LocalDateTime scheduledFireTime = context.getScheduledFireTime() == null ? null : context.getScheduledFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // fireTime：任务执行时间
        LocalDateTime fireTime = context.getFireTime() == null ? null : context.getFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // delay：任务延迟时间
        long delay = -1;
        if(scheduledFireTime != null && fireTime != null){
            Duration duration = Duration.between(scheduledFireTime, fireTime);
            delay = duration.toMillis();
        }

        // jobRunTime：任务执行耗时
        long jobRunTime = context.getJobRunTime();

        // nextFireTime：任务上次执行时间
        LocalDateTime previousFireTime = context.getPreviousFireTime() == null ? null : context.getPreviousFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // nextFireTime：任务下次执行时间
        LocalDateTime nextFireTime = context.getNextFireTime() == null ? null : context.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        taskRecord.setTaskId(taskId);
        taskRecord.setScheduledFireTime(scheduledFireTime);
        taskRecord.setFireTime(fireTime);
        taskRecord.setDelayTime(delay);
        taskRecord.setJobRunTime(jobRunTime);
        taskRecord.setPreviousFireTime(previousFireTime);
        taskRecord.setNextFireTime(nextFireTime);
        taskRecord.setVetoed(vetoed);
        taskRecord.setIsError(isError);
        taskRecord.setErrorMessage(errorMessage);
        taskRecordService.save(taskRecord);

    }

}
