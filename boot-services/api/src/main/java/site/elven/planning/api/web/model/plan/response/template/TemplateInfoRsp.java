package site.elven.planning.api.web.model.plan.response.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.plan.TemplateType;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateInfoRsp extends BaseRsp {
    @JsonProperty("username")
    private String username;
    @JsonProperty("template_code")
    private String templateCode;
    @JsonProperty("template_name")
    private String templateName;
    @JsonProperty("template_intro")
    private String templateIntro;
    @JsonProperty("template_type")
    private TemplateType templateType;
    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty("enabled")
    private Boolean enabled;
}
