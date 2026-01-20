package site.elven.planning.api.web.model.plan.response.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskDetailRsp extends TaskInfoRsp {
    @JsonProperty("sub_tasks")
    private List<TaskDetailRsp> subTasks;
    @JsonProperty("properties")
    private Map<String, Object> properties;
}
