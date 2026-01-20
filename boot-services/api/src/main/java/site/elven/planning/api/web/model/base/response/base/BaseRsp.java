package site.elven.planning.api.web.model.base.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRsp {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty("deleted")
    private Boolean deleted;
    @JsonProperty("deleted_id")
    private Long deletedId;
    @JsonProperty("version")
    private Integer version;
}
