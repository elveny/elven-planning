package site.elven.planning.api.web.model.scheduler.response.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.scheduler.AppStatus;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppRegistryRsp extends BaseRsp {
    @JsonProperty("app_code")
    private String appCode;
    @JsonProperty("app_name")
    private String appName;
    @JsonProperty("app_secret")
    private String appSecret;
    @JsonProperty("app_desc")
    private String appDesc;
    @JsonProperty("app_status")
    private AppStatus appStatus;
    @JsonProperty("group_name")
    private String groupName;
}
