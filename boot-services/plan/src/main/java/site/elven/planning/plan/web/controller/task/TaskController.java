package site.elven.planning.plan.web.controller.task;

import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.plan.request.task.TaskInfoReq;
import site.elven.planning.api.web.model.plan.response.task.TaskDeleteRsp;
import site.elven.planning.api.web.model.plan.response.task.TaskDetailRsp;
import site.elven.planning.api.web.model.plan.response.task.TaskInfoRsp;
import site.elven.planning.plan.web.service.task.TaskInfoService;

@RestController
@RequestMapping("/web/v1/task")
public class TaskController {
    private final TaskInfoService taskInfoService;

    public TaskController(TaskInfoService taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    @RequestMapping("/list")
    public PageRsp<TaskInfoRsp> list(@RequestBody TaskInfoReq taskInfoReq) {
        return taskInfoService.list(taskInfoReq);
    }

    @RequestMapping("/create")
    public TaskDetailRsp create(@RequestBody TaskInfoReq taskInfoReq) {
        return taskInfoService.save(taskInfoReq);
    }

    @RequestMapping(value = "/{task_id}", method = RequestMethod.PUT)
    public TaskDetailRsp update(@PathVariable("task_id") long taskId, @RequestBody TaskInfoReq taskInfoReq) {
        return taskInfoService.update(taskId, taskInfoReq);
    }

    @RequestMapping(value = "/{task_id}", method = RequestMethod.GET)
    public TaskDetailRsp detail(@PathVariable("task_id") long taskId) {
        return taskInfoService.detail(taskId);
    }

    @RequestMapping(value = "/{task_id}", method = RequestMethod.DELETE)
    public TaskDeleteRsp delete(@PathVariable("task_id") long taskId) {
        taskInfoService.logicDeleteById(taskId);
        return new TaskDeleteRsp(true);
    }

}
