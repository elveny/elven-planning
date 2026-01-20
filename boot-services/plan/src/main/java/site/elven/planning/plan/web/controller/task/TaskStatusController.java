package site.elven.planning.plan.web.controller.task;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.common.enums.plan.TaskStatus;
import site.elven.planning.api.web.model.plan.response.task.TaskStatusRsp;
import site.elven.planning.plan.web.service.task.TaskInfoService;

@RestController
@RequestMapping("/web/v1/task/status")
public class TaskStatusController {
    private final TaskInfoService taskInfoService;
    public TaskStatusController(TaskInfoService taskInfoService) {
        this.taskInfoService = taskInfoService;
    }


    @RequestMapping(value = "/{task_id}/start", method = RequestMethod.PUT)
    public TaskStatusRsp start(@PathVariable("task_id") long taskId) {
        int n = taskInfoService.updateStatus(taskId, TaskStatus.processing);
        return new TaskStatusRsp(taskId, TaskStatus.processing, n == 1);
    }

    @RequestMapping(value = "/{task_id}/end", method = RequestMethod.PUT)
    public TaskStatusRsp end(@PathVariable("task_id") long taskId) {
        int n = taskInfoService.updateStatus(taskId, TaskStatus.complete);
        return new TaskStatusRsp(taskId, TaskStatus.complete, n == 1);
    }

    @RequestMapping(value = "/{task_id}/delay", method = RequestMethod.PUT)
    public TaskStatusRsp delay(@PathVariable("task_id") long taskId) {
        int n = taskInfoService.updateStatus(taskId, TaskStatus.delay);
        return new TaskStatusRsp(taskId, TaskStatus.delay, n == 1);
    }

    @RequestMapping(value = "/{task_id}/cancel", method = RequestMethod.PUT)
    public TaskStatusRsp cancel(@PathVariable("task_id") long taskId) {
        int n = taskInfoService.updateStatus(taskId, TaskStatus.cancel);
        return new TaskStatusRsp(taskId, TaskStatus.cancel, n == 1);
    }

    @RequestMapping(value = "/{task_id}/reset", method = RequestMethod.PUT)
    public TaskStatusRsp reset(@PathVariable("task_id") long taskId) {
        int n = taskInfoService.updateStatus(taskId, TaskStatus.pending);
        return new TaskStatusRsp(taskId, TaskStatus.pending, n == 1);
    }

}
