package site.elven.planning.api.web.model.plan.request.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.common.enums.plan.TemplateType;
import site.elven.planning.api.web.model.base.request.page.PageReq;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateInfoReq extends PageReq {
    @JsonProperty("username")
    private String username;
    @JsonProperty("template_name")
    private String templateName;
    @JsonProperty("template_code")
    private String templateCode;
    @JsonProperty("template_intro")
    private String templateIntro;
    @JsonProperty("template_type")
    private TemplateType templateType;
    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("properties")
    private List<TemplatePropertyReq> properties;
}
