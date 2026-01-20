package site.elven.planning.scheduler.quartz.job.defaults;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import site.elven.common.gson.GsonUtils;
import site.elven.planning.scheduler.quartz.job.base.BaseJob;

@Slf4j
public class DefaultJob extends BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        long fireTime = context.getFireTime().getTime();
        log.info("DefaultJob execute, fireTime: {}, jobDataMap >> {}", fireTime, GsonUtils.toJson(mergedJobDataMap));
    }
}
