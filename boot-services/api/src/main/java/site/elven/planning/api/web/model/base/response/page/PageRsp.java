package site.elven.planning.api.web.model.base.response.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageRsp<T> {
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private List<T> content;
}
