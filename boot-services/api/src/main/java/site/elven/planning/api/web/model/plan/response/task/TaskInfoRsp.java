package site.elven.planning.api.web.model.plan.response.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;
import site.elven.planning.api.common.enums.plan.TaskMode;
import site.elven.planning.api.common.enums.plan.TaskStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskInfoRsp extends BaseRsp {
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
}
