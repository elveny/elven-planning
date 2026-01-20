package site.elven.planning.api.web.model.base.request.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.api.web.model.base.request.base.BaseReq;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePropertyReq extends BaseReq {
    @JsonProperty("data_id")
    private Long dataId;
    @JsonProperty("property_code")
    private String propertyCode;
    @JsonProperty("property_name")
    private String propertyName;
    @JsonProperty("property_desc")
    private String propertyDesc;
    @JsonProperty("property_type")
    private CommonConfigPropertyType propertyType;
    @JsonProperty("property_value")
    private String propertyValue;
    @JsonProperty("property_extend")
    private String propertyExtend;
    @JsonProperty("property_sort")
    private Integer propertySort;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("enabled")
    private Boolean enabled = true;
}
