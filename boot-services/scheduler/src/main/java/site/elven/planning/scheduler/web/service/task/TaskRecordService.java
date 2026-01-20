package site.elven.planning.scheduler.web.service.task;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.task.TaskRecordReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.scheduler.web.dal.task.TaskRecordRepository;
import site.elven.planning.scheduler.web.model.entity.task.TaskRecord;

public interface TaskRecordService extends IService<TaskRecord, Long, TaskRecordRepository> {
    PageRsp<TaskRecordRsp> list(TaskRecordReq taskRecordReq);
}
