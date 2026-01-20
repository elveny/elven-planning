package site.elven.planning.api.web.model.base.request.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.base.CommonConfigSourceCode;
import site.elven.planning.api.web.model.base.request.base.BaseReq;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommonConfigInfoReq extends BaseReq {
    private String username;
    private CommonConfigSourceCode sourceCode;
    private Long sourceId;
    private String remark;
    private Boolean enabled;
}
