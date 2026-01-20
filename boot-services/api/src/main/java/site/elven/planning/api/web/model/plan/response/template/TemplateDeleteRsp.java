package site.elven.planning.api.web.model.plan.response.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.web.model.base.response.base.BaseDeleteRsp;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TemplateDeleteRsp extends BaseDeleteRsp {
    public TemplateDeleteRsp(boolean deleted) {
        super(deleted);
    }
}
