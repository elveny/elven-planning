package site.elven.planning.scheduler.web.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.task.TaskRecordReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.scheduler.web.service.task.TaskRecordService;

@RestController
@RequestMapping("/web/v1/task/record")
public class TaskRecordController {

    @Autowired
    private TaskRecordService taskRecordService;

    @RequestMapping("/list")
    public PageRsp<TaskRecordRsp> list(@RequestBody TaskRecordReq taskRecordReq) {
        return taskRecordService.list(taskRecordReq);
    }
}
