package site.elven.planning.api.web.model.scheduler.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.web.model.base.request.page.PageReq;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskScheduleReq extends TaskInfoReq {
    @JsonProperty("job_params")
    private Map<String, Object> jobParams;
}
