package site.elven.planning.api.web.model.scheduler.request.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppAddTaskReq extends AppTaskReq {
    @JsonProperty("app_secret")
    private String appSecret;

}
