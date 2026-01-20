package site.elven.planning.api.web.model.scheduler.request.http;

import lombok.Data;

@Data
public class HttpClientProperty {
    private String url;
    private String method;
    private String json;
    private long connectTimeout;
    private long readTimeout;
}
