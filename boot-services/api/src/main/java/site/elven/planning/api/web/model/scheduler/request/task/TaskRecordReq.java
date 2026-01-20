package site.elven.planning.api.web.model.scheduler.request.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.base.request.page.PageReq;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskRecordReq extends PageReq {
    private Long taskId;
    private Boolean vetoed;
    private Boolean isError;
    private String errorMessage;
    private Long delayTime;
    private Long delayTimeMin;
    private Long delayTimeMax;
    private Long jobRunTime;
    private Long jobRunTimeMin;
    private Long jobRunTimeMax;
    private LocalDateTime fireTime;
    private LocalDateTime fireTimeMin;
    private LocalDateTime fireTimeMax;
}
