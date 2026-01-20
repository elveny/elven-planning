package site.elven.planning.api.web.model.scheduler.request.simple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryTaskScheduleReq extends TaskScheduleReq {
}
