package site.elven.planning.api.web.model.plan.response.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.base.response.base.BaseDeleteRsp;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDeleteRsp extends BaseDeleteRsp {
    public TaskDeleteRsp(boolean deleted) {
        super(deleted);
    }
}
