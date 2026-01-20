package site.elven.planning.api.web.model.scheduler.request.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.base.request.page.PageReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppTaskReq extends PageReq {
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
