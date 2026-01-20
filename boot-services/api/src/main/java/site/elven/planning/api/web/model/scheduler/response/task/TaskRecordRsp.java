package site.elven.planning.api.web.model.scheduler.response.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskRecordRsp extends BaseRsp {
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("scheduled_fire_time")
    private LocalDateTime scheduledFireTime;
    @JsonProperty("fire_time")
    private LocalDateTime fireTime;
    @JsonProperty("delay_time")
    private Long delayTime;
    @JsonProperty("job_run_time")
    private Long jobRunTime;
    @JsonProperty("previous_fire_time")
    private LocalDateTime previousFireTime;
    @JsonProperty("next_fire_time")
    private LocalDateTime nextFireTime;
    @JsonProperty("vetoed")
    private Boolean vetoed;
    @JsonProperty("is_error")
    private Boolean isError;
    @JsonProperty("error_message")
    private String errorMessage;
}
