package site.elven.planning.api.web.model.plan.response.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateDetailRsp extends TemplateInfoRsp {
    @JsonProperty("properties")
    private List<TemplatePropertyRsp> properties;
}
