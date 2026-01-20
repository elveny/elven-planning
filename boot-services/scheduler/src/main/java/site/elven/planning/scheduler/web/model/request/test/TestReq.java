package site.elven.planning.scheduler.web.model.request.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.base.request.page.PageReq;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestReq extends PageReq {
    @JsonProperty("task_name")
    private String taskName;
    @JsonProperty("task_desc")
    private String taskDesc;
    @JsonProperty("task_status")
    private TaskStatus taskStatus;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("job_name")
    private String jobName;
    @JsonProperty("trigger_name")
    private String triggerName;
    @JsonProperty("job_class_name")
    private String jobClassName;
    @JsonProperty("cron")
    private String cron;
    @JsonProperty("enabled")
    private Boolean enabled = true;
    @JsonProperty("map_params")
    private Map<String, Object> mapParams;

    private TestProperty property;
}
