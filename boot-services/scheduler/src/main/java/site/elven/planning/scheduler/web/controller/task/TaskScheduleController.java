package site.elven.planning.scheduler.web.controller.task;

import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskScheduleRsp;
import site.elven.planning.scheduler.web.model.entity.task.TaskInfo;
import site.elven.planning.scheduler.web.service.task.TaskInfoService;
import site.elven.planning.scheduler.web.service.task.TaskScheduleService;

@RestController
@RequestMapping("/web/v1/task/schedule")
public class TaskScheduleController {

    private final TaskInfoService taskInfoService;

    private final TaskScheduleService taskScheduleService;

    public TaskScheduleController(TaskInfoService taskInfoService, TaskScheduleService taskScheduleService) {
        this.taskInfoService = taskInfoService;
        this.taskScheduleService = taskScheduleService;
    }

    @RequestMapping("/list")
    public PageRsp<TaskInfoRsp> list(@RequestBody TaskScheduleReq taskScheduleReq) {
        return taskInfoService.list(taskScheduleReq);
    }

    @RequestMapping("/detail")
    public TaskScheduleRsp detail(@RequestParam("task_id") long taskId) {
        return taskInfoService.detail(taskId);
    }

    @RequestMapping("/create")
    public TaskScheduleRsp create(@RequestBody TaskScheduleReq taskScheduleReq) throws SchedulerException {
        TaskInfoRsp taskInfo = taskScheduleService.createTask(taskScheduleReq);
        return new TaskScheduleRsp("create", true, taskInfo, null, null);
    }

    @RequestMapping("/start")
    public TaskScheduleRsp start(@RequestParam("task_id") long taskId) {
        boolean result = taskScheduleService.startTask(taskId);
        return new TaskScheduleRsp("start", result, null, null, null);
    }

    @RequestMapping("/pause")
    public TaskScheduleRsp pause(@RequestParam("task_id") long taskId) {
        boolean result = taskScheduleService.pauseTask(taskId);
        return new TaskScheduleRsp("pause", result, null, null, null);
    }

    @RequestMapping("/resume")
    public TaskScheduleRsp resume(@RequestParam("task_id") long taskId) {
        boolean result = taskScheduleService.resumeTask(taskId);
        return new TaskScheduleRsp("resume", result, null, null, null);
    }

    @RequestMapping("/delete")
    public TaskScheduleRsp delete(@RequestParam("task_id") long taskId) {
        boolean result = taskScheduleService.deleteTask(taskId);
        return new TaskScheduleRsp("delete", result, null, null, null);
    }
}
