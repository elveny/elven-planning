package site.elven.planning.api.web.model.plan.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.plan.TaskMode;
import site.elven.planning.api.common.enums.plan.TaskStatus;
import site.elven.planning.api.web.model.base.request.page.PageReq;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskInfoReq extends PageReq {
    @JsonProperty("username")
    private String username;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("mode")
    private TaskMode mode;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("status")
    private TaskStatus status;
    @JsonProperty("is_public")
    private Boolean isPublic;
    @JsonProperty("template_id")
    private Long templateId;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("properties")
    private List<TaskPropertyReq> properties;
}
