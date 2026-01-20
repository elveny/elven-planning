package site.elven.planning.api.web.model.base.response.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.base.CommonConfigSourceCode;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonConfigInfoRsp extends BaseRsp {
    @JsonProperty("username")
    private String username;
    @JsonProperty("source_code")
    private CommonConfigSourceCode sourceCode;
    @JsonProperty("source_id")
    private Long sourceId;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("enabled")
    private Boolean enabled = true;
}
