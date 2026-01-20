package site.elven.planning.api.web.model.scheduler.response.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskScheduleRsp extends BaseRsp {
    private String type;
    private boolean result;
    private TaskInfoRsp task;
    private Map<String, Object> taskProperties;
    private TaskRecordRsp record;
}
