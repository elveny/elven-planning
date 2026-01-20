package site.elven.planning.plan.web.service.task.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.api.common.enums.plan.TaskStatus;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.plan.common.exception.ExceptionCode;
import site.elven.planning.plan.common.exception.PlanException;
import site.elven.planning.plan.web.dal.task.TaskInfoRepository;
import site.elven.planning.plan.web.mapstruct.task.TaskInfoMapStruct;
import site.elven.planning.plan.web.mapstruct.task.TaskPropertyMapStruct;
import site.elven.planning.plan.web.model.entity.task.TaskInfo;
import site.elven.planning.plan.web.model.entity.task.TaskProperty;
import site.elven.planning.api.web.model.plan.request.task.TaskInfoReq;
import site.elven.planning.api.web.model.plan.response.task.TaskDetailRsp;
import site.elven.planning.api.web.model.plan.response.task.TaskInfoRsp;
import site.elven.planning.plan.web.service.task.TaskInfoService;
import site.elven.planning.plan.web.service.task.TaskPropertyService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfo, Long, TaskInfoRepository> implements TaskInfoService {

    private final TaskPropertyService taskPropertyService;
    private final TaskInfoMapStruct taskInfoMapStruct;
    private final TaskPropertyMapStruct taskPropertyMapStruct;

    public TaskInfoServiceImpl(TaskPropertyService taskPropertyService, TaskInfoMapStruct taskInfoMapStruct, TaskPropertyMapStruct taskPropertyMapStruct) {
        this.taskPropertyService = taskPropertyService;
        this.taskInfoMapStruct = taskInfoMapStruct;
        this.taskPropertyMapStruct = taskPropertyMapStruct;
    }

    @Override
    public TaskDetailRsp save(TaskInfoReq taskInfoReq) {
        TaskInfo taskInfo = taskInfoMapStruct.req2Entity(taskInfoReq);
        List<TaskProperty> properties = taskPropertyMapStruct.req2Entity(taskInfoReq.getProperties());
        taskInfo = this.save(taskInfo);
        Long taskId = taskInfo.getId();
        log.info("保存计划任务信息：{}", taskId);

        if(!CollectionUtils.isEmpty(properties)){
            for (TaskProperty property : properties){
                property.setDataId(taskId);
                String propertyCode = property.getPropertyCode();
                String propertyName = property.getPropertyName();
                CommonConfigPropertyType propertyType = property.getPropertyType();
                if(propertyName == null){
                    property.setPropertyName(propertyCode);
                }
                if(propertyType == null){
                    property.setPropertyType(CommonConfigPropertyType.STRING);
                }
            }
            taskPropertyService.saveAll(properties);
        }

        // 对象转换
        TaskDetailRsp taskDetailRsp = entity2Detail(taskInfo);
        taskDetailRsp.setProperties(convertProperties(properties));

        return taskDetailRsp;
    }

    @Override
    public TaskDetailRsp update(long taskId, TaskInfoReq taskInfoReq) {
        Optional<TaskInfo> optional = repository.findById(taskId);

        TaskInfo taskInfo = optional.orElseThrow(() -> new PlanException(ExceptionCode.PLAN_DEFAULT.getCode(), "Task not found"));

        // 修改主数据
        taskInfo.setUsername(taskInfoReq.getUsername());
        taskInfo.setTitle(taskInfoReq.getTitle());
        taskInfo.setDescription(taskInfoReq.getDescription());
        taskInfo.setMode(taskInfoReq.getMode());
        taskInfo.setStatus(taskInfoReq.getStatus());
        taskInfo.setIsPublic(taskInfoReq.getIsPublic());
        taskInfo.setTemplateId(taskInfoReq.getTemplateId());
        taskInfo = repository.save(taskInfo);

        // 逻辑删除属性
        int result = taskPropertyService.logicDeleteAllInBatch(taskPropertyService.findByDataId(taskId));

        // 保存属性
        List<TaskProperty> properties = taskPropertyMapStruct.req2Entity(taskInfoReq.getProperties());

        if(!CollectionUtils.isEmpty(properties)){
            for (TaskProperty property : properties){
                property.setDataId(taskId);
                String propertyCode = property.getPropertyCode();
                String propertyName = property.getPropertyName();
                CommonConfigPropertyType propertyType = property.getPropertyType();
                if(propertyName == null){
                    property.setPropertyName(propertyCode);
                }
                if(propertyType == null){
                    property.setPropertyType(CommonConfigPropertyType.STRING);
                }
            }
            taskPropertyService.saveAll(properties);
        }

        TaskDetailRsp taskDetailRsp = entity2Detail(taskInfo);
        taskDetailRsp.setProperties(convertProperties(properties));

        return taskDetailRsp;

    }

    @Override
    public int updateStatus(long taskId, TaskStatus status) {
        TaskInfo taskInfo = repository.findByIdAndNotDeleted(taskId).orElse(null);
        if (taskInfo == null){
            throw new PlanException(ExceptionCode.PLAN_TASK_IS_NULL.getCode(), "task is null");
        }
        // todo: 是否根据现有状态确定是否允许到一下个状态

        taskInfo.setStatus(status);
        repository.save(taskInfo);
        return 1;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDetailRsp detail(long taskId) {
        enableDeleteFilter();
        Optional<TaskInfo> optional = repository.findByIdAndNotDeleted(taskId);
        if(optional.isPresent()){
            TaskInfo taskInfo = optional.get();
            Long taskInfoId = taskInfo.getId();
            TaskDetailRsp taskDetailRsp = entity2Detail(taskInfo);
            taskDetailRsp.setSubTasks(getSubTasks(taskInfoId));
            taskDetailRsp.setProperties(getProperties(taskInfoId));
            return taskDetailRsp;
        }
        return null;
    }

    public List<TaskDetailRsp> getSubTasks(long taskId) {
        List<TaskDetailRsp> subTasks = new ArrayList<>();
        List<TaskInfo> taskInfoList = repository.findAllByParentId(taskId);
        if(taskInfoList != null){
            for (TaskInfo taskInfo : taskInfoList){
                TaskInfoRsp taskInfoRsp = taskInfoMapStruct.entity2Rsp(taskInfo);
                TaskDetailRsp taskDetailRsp = taskInfoMapStruct.toDetail(taskInfoRsp);
                Long taskInfoId = taskInfo.getId();
                taskDetailRsp.setSubTasks(getSubTasks(taskInfoId));
                taskDetailRsp.setProperties(getProperties(taskInfoId));
                subTasks.add(taskDetailRsp);
            }
        }

        return subTasks;
    }

    public Map<String, Object> getProperties(long taskId){
        List<TaskProperty> properties = taskPropertyService.findByDataId(taskId);

        return convertProperties(properties);
    }

    @Override
    public PageRsp<TaskInfoRsp> list(TaskInfoReq taskInfoReq) {
        String username = taskInfoReq.getUsername();
        String title = taskInfoReq.getTitle();
        String description = taskInfoReq.getDescription();
        TaskStatus taskStatus = taskInfoReq.getStatus();
        Boolean isPublic = taskInfoReq.getIsPublic();
        Long templateId = taskInfoReq.getTemplateId();

        TaskInfo search = new TaskInfo();
        Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers = new HashMap<>();
        if(StringUtils.hasText(username)){
            search.setUsername(username);
        }
        if(StringUtils.hasText(title)){
            search.setTitle(title);
            matchers.put("title", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(description)){
            search.setDescription(description);
            matchers.put("description", match -> match.startsWith().ignoreCase());
        }
        if(taskStatus != null){
            search.setStatus(taskStatus);
        }
        if (isPublic != null){
            search.setIsPublic(isPublic);
        }
        if(templateId != null && templateId > 0){
            search.setTemplateId(templateId);
        }

        // 分页查询数据
        PageRsp<TaskInfo> pageRsp = list(search, taskInfoReq.getPageNumber(), taskInfoReq.getPageSize(), null, null, matchers);
        // 转换数据
        List<TaskInfoRsp> list = pageRsp.getContent().stream().map(taskInfoMapStruct::entity2Rsp).collect(Collectors.toList());
        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), list);
    }

    @Override
    public int logicDeleteById(Long id) {
        taskPropertyService.logicDeleteAllInBatch(taskPropertyService.findByDataId(id));
        return super.logicDeleteById(id);
    }

    private Map<String, Object> convertProperties(List<TaskProperty> properties) {
        Map<String, Object> taskProperties = new HashMap<>();
        if(properties != null){
            for (TaskProperty property : properties){
                Object value = property.getPropertyType().convert(property.getPropertyValue(), property.getPropertyExtend());
                taskProperties.put(property.getPropertyCode(), value);
            }
        }
        return taskProperties;
    }

    private TaskDetailRsp entity2Detail(TaskInfo taskInfo){
        TaskInfoRsp taskInfoRsp = taskInfoMapStruct.entity2Rsp(taskInfo);
        return taskInfoMapStruct.toDetail(taskInfoRsp);
    }

}
