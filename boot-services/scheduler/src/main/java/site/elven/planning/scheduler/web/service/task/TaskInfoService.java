package site.elven.planning.scheduler.web.service.task;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskScheduleRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.scheduler.web.dal.task.TaskInfoRepository;
import site.elven.planning.scheduler.web.model.entity.task.TaskInfo;

import java.util.Map;

public interface TaskInfoService extends IService<TaskInfo, Long, TaskInfoRepository> {
    TaskInfo save(TaskInfo taskInfo, Map<String, Object> properties);

    /**
     * 查询任务列表
     * @param taskScheduleReq 请求参数
     * @return 任务列表
     */
    PageRsp<TaskInfoRsp> list(TaskScheduleReq taskScheduleReq);

    /**
     * 查询任务详情
     * @param taskId 任务ID
     * @return 任务详情
     */
    TaskScheduleRsp detail(long taskId);

    /**
     * 查询任务属性
     * @param taskId 任务ID
     * @return 任务属性
     */
    Map<String, Object> getTaskProperties(long taskId);

    /**
     * 查询任务最后一次执行记录
     * @param taskId 任务ID
     * @return 任务最后一次执行记录
     */
    TaskRecordRsp getLastTaskRecord(long taskId);
}
