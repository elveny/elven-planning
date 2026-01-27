package site.elven.planning.scheduler.web.service.task.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.api.common.enums.base.CommonConfigSourceCode;
import site.elven.planning.api.web.model.base.request.config.CommonConfigInfoReq;
import site.elven.planning.api.web.model.base.response.config.CommonConfigDetailRsp;
import site.elven.planning.api.web.model.base.response.config.CommonConfigPropertyRsp;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.task.TaskRecordReq;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;
import site.elven.planning.api.web.model.scheduler.response.task.TaskInfoRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskScheduleRsp;
import site.elven.planning.boot.base.common.constant.CommonConstants;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigInfo;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.boot.common.web.service.config.CommonConfigInfoService;
import site.elven.planning.scheduler.web.dal.task.TaskInfoRepository;
import site.elven.planning.scheduler.web.mapstruct.task.TaskInfoMapStruct;
import site.elven.planning.scheduler.web.model.entity.task.TaskInfo;
import site.elven.planning.scheduler.web.service.task.TaskInfoService;
import site.elven.planning.scheduler.web.service.task.TaskRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfo, Long, TaskInfoRepository> implements TaskInfoService {
    @Autowired
    private CommonConfigInfoService commonConfigInfoService;
    @Autowired
    private TaskRecordService taskRecordService;
    @Autowired
    private TaskInfoMapStruct taskInfoMapStruct;

    @Override
    public TaskInfo save(TaskInfo taskInfo, Map<String, Object> taskProperties) {
        String username = taskInfo.getUsername();
        if (username == null){
            taskInfo.setUsername(CommonConstants.COMMON_DEFAULT_SYSTEM_USER);
        }
        TaskInfo info = this.save(taskInfo);

        CommonConfigInfo commonConfigInfo = new CommonConfigInfo();
        commonConfigInfo.setSourceCode(CommonConfigSourceCode.task);
        commonConfigInfo.setSourceId(info.getId());

        List<CommonConfigProperty> properties = null;
        if(taskProperties != null){
            properties = new ArrayList<>(taskProperties.size());
            for (Map.Entry<String, Object> entry : taskProperties.entrySet()) {
                CommonConfigProperty property = new CommonConfigProperty();
                property.setPropertyCode(entry.getKey());
                property.setPropertyValue(entry.getValue() == null ? null : entry.getValue().toString());
                properties.add(property);
            }
        }
        commonConfigInfoService.save(commonConfigInfo, properties);

        return info;
    }

    @Override
    public PageRsp<TaskInfoRsp> list(TaskScheduleReq taskScheduleReq) {
        String taskName = taskScheduleReq.getTaskName();
        String groupName = taskScheduleReq.getGroupName();
        String jobName = taskScheduleReq.getJobName();
        String triggerName = taskScheduleReq.getTriggerName();
        String jobClassName = taskScheduleReq.getJobClassName();

        TaskInfo search = new TaskInfo();
//        search.setDeleted(false);
        if (taskName != null && !taskName.isEmpty()){
            search.setTaskName(taskName);
        }
        if (groupName != null && !groupName.isEmpty()){
            search.setGroupName(groupName);
        }
        if (jobName != null && !jobName.isEmpty()){
            search.setJobName(jobName);
        }
        if (triggerName != null && !triggerName.isEmpty()){
            search.setTriggerName(triggerName);
        }
        if (jobClassName != null && !jobClassName.isEmpty()){
            search.setJobClassName(jobClassName);
        }

        Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers = Map.of("jobClassName", match -> match.startsWith().ignoreCase());

        PageRsp<TaskInfo> pageRsp = list(search, taskScheduleReq.getPageNumber(), taskScheduleReq.getPageSize(), null, null, matchers);
        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), taskInfoMapStruct.entity2Rsp(pageRsp.getContent()));
    }

    @Override
    public TaskScheduleRsp detail(long taskId) {
        // 获取任务信息
        TaskInfo task = findById(taskId);
        Map<String, Object> taskProperties = null;
        TaskRecordRsp taskRecord = null;
        if (task != null){
            // 获取任务配置信息
            taskProperties = getTaskProperties(taskId);
            // 获取最近一条任务执行记录
            taskRecord = getLastTaskRecord(taskId);
        }
        return new TaskScheduleRsp("detail", true, taskInfoMapStruct.entity2Rsp(task), taskProperties, taskRecord);
    }

    @Override
    public Map<String, Object> getTaskProperties(long taskId) {
        Map<String, Object> taskProperties = null;
        // 获取任务配置信息
        CommonConfigInfoReq commonConfigInfoReq = new CommonConfigInfoReq();
        commonConfigInfoReq.setSourceCode(CommonConfigSourceCode.task);
        commonConfigInfoReq.setSourceId(taskId);
        CommonConfigDetailRsp commonConfigDetailRsp = commonConfigInfoService.detail(commonConfigInfoReq);

        // 将任务配置信息转换为Map
        if (commonConfigDetailRsp != null
                && commonConfigDetailRsp.getProperties() != null
                && commonConfigDetailRsp.getProperties().size() > 0){
            taskProperties = commonConfigDetailRsp.getProperties().stream().collect(Collectors.toMap(CommonConfigPropertyRsp::getPropertyCode, entity -> {
                if (entity.getPropertyValue() == null
                        && CommonConfigPropertyType.STRING == entity.getPropertyType()
                        && entity.getPropertyExtend() != null) {
                    return entity.getPropertyExtend();
                }
                return entity.getPropertyValue() == null ? "" : entity.getPropertyValue();
            }, (existing, replacement) -> existing));
        }
        return taskProperties;
    }

    @Override
    public TaskRecordRsp getLastTaskRecord(long taskId) {
        TaskRecordRsp taskRecord = null;
        // 获取任务执行记录
        TaskRecordReq taskRecordReq = new TaskRecordReq();
        taskRecordReq.setTaskId(taskId);
        taskRecordReq.setPageNumber(1);
        taskRecordReq.setPageSize(1);
        PageRsp<TaskRecordRsp> pageRsp = taskRecordService.list(taskRecordReq);

        // 取第一条任务执行记录
        if (pageRsp !=null && pageRsp.getContent().size() > 0){
            taskRecord = pageRsp.getContent().get(0);
        }
        return taskRecord;
    }

}
