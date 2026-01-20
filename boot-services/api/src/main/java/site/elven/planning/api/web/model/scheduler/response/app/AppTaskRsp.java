package site.elven.planning.api.web.model.scheduler.response.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppTaskRsp extends BaseRsp {
    @JsonProperty("app_code")
    private String appCode;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("task_name")
    private String taskName;
    @JsonProperty("task_desc")
    private String taskDesc;
    @JsonProperty("task_status")
    private TaskStatus taskStatus;
    @JsonProperty("params")
    private String params;
    @JsonProperty("task_id")
    private Long taskId;
}
