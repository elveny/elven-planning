package site.elven.planning.api.web.model.base.response.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonConfigDetailRsp extends BaseRsp {
    private CommonConfigInfoRsp config;
    private List<CommonConfigPropertyRsp> properties;
}
