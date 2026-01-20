package site.elven.planning.api.web.model.base.request.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.elven.planning.api.web.model.base.request.base.BaseReq;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageReq extends BaseReq {
    @JsonProperty("page_number")
    private int pageNumber;
    @JsonProperty("page_size")
    private int pageSize;
}
