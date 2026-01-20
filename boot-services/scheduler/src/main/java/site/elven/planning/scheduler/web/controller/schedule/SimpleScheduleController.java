package site.elven.planning.scheduler.web.controller.schedule;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.scheduler.web.service.schedule.ScheduleService;

@RestController
@RequestMapping("/web/v1/schedule/simple")
public class SimpleScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/task/create")
    public String create(@RequestParam("group_name") String groupName, @RequestParam("job_name") String jobName, @RequestParam("trigger_name") String triggerName,
                         @RequestParam("job_class_name") String jobClassName, @RequestParam("interval_ms") long intervalMs) throws SchedulerException {
        scheduleService.createTask(groupName, jobName, triggerName, jobClassName, null, intervalMs);
        return "create";
    }

    @RequestMapping("/task/delete")
    public String delete(@RequestParam("group_name") String groupName, @RequestParam("job_name") String jobName, @RequestParam("trigger_name") String triggerName) throws SchedulerException {
        scheduleService.deleteTask(groupName, jobName, triggerName);
        return "delete";
    }

    @RequestMapping("/task/pause")
    public String pause(@RequestParam("group_name") String groupName, @RequestParam("job_name") String jobName, @RequestParam("trigger_name") String triggerName) throws SchedulerException {
        scheduleService.pauseTask(groupName, jobName, triggerName);
        return "pause";
    }

    @RequestMapping("/task/resume")
    public String resume(@RequestParam("group_name") String groupName, @RequestParam("job_name") String jobName, @RequestParam("trigger_name") String triggerName) throws SchedulerException {
        scheduleService.resumeTask(groupName, jobName, triggerName);
        return "resume";
    }

}
