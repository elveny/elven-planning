package site.elven.planning.scheduler.web.model.request.test;

import lombok.Data;

@Data
public class TestProperty {
    private String url;
    private String method;
    private String json;
    private long connectTimeout;
    private long readTimeout;
}
