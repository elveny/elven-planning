package site.elven.planning.plan.web.service.task;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.api.common.enums.plan.TaskStatus;
import site.elven.planning.plan.web.dal.task.TaskInfoRepository;
import site.elven.planning.plan.web.model.entity.task.TaskInfo;
import site.elven.planning.api.web.model.plan.request.task.TaskInfoReq;
import site.elven.planning.api.web.model.plan.response.task.TaskDetailRsp;
import site.elven.planning.api.web.model.plan.response.task.TaskInfoRsp;

import java.util.List;
import java.util.Map;

public interface TaskInfoService extends IService<TaskInfo, Long, TaskInfoRepository> {
    /**
     * 保存计划任务
     * @param taskInfoReq 模板请求参数
     * @return 任务信息
     */
    TaskDetailRsp save(TaskInfoReq taskInfoReq);

    /**
     * 更新计划任务
     * @param taskId 模板ID
     * @param taskInfoReq 模板请求参数
     * @return 任务详情
     */
    TaskDetailRsp update(long taskId, TaskInfoReq taskInfoReq);

    /**
     * 更新计划任务状态
     * @param taskId 模板ID
     * @param status 状态
     * @return 更新结果
     */
    int updateStatus(long taskId, TaskStatus status);

    /**
     * 获取计划任务详情
     * @param taskId 任务模板ID
     * @return 任务详情
     */
    TaskDetailRsp detail(long taskId);

    /**
     * 获取计划任务子任务列表
     * @param taskId 模板ID
     * @return 子任务列表
     */
    List<TaskDetailRsp> getSubTasks(long taskId);

    /**
     * 获取计划任务属性列表
     * @param taskId 模板ID
     * @return 属性列表
     */
    Map<String, Object> getProperties(long taskId);

    /**
     * 获取计划任务列表
     * @param taskInfoReq 模板请求参数
     * @return 任务列表
     */
    PageRsp<TaskInfoRsp> list(TaskInfoReq taskInfoReq);

    /**
     * 逻辑删除计划任务
     * @param id 模板ID
     * @return 删除结果
     */
    int logicDeleteById(Long id);

}
