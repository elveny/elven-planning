package site.elven.planning.api.web.model.plan.response.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.plan.TaskStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusRsp {
    private Long taskId;
    private TaskStatus status;
    private Boolean result;
}
