package site.elven.planning.api.web.model.scheduler.response.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.common.enums.scheduler.TaskType;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskInfoRsp extends BaseRsp {
    @JsonProperty("username")
    private String username;
    @JsonProperty("task_type")
    private TaskType taskType;
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
    @JsonProperty("cron_expression")
    private String cronExpression;
    @JsonProperty("enabled")
    private Boolean enabled = true;
}
