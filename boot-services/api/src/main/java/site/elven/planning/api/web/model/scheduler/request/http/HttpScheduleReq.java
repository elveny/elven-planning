package site.elven.planning.api.web.model.scheduler.request.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.scheduler.request.task.TaskScheduleReq;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpScheduleReq extends TaskScheduleReq {
    @JsonProperty("http_client_property")
    private HttpClientProperty httpClientProperty;
}
