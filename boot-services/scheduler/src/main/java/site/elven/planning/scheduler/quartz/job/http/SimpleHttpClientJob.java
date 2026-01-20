package site.elven.planning.scheduler.quartz.job.http;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import site.elven.planning.common.util.http.HttpUtils;
import site.elven.planning.scheduler.quartz.job.base.BaseJob;
import site.elven.planning.scheduler.web.service.schedule.ScheduleService;

@Slf4j
public class SimpleHttpClientJob extends BaseJob {

    @Autowired
    private ScheduleService scheduleService;

    private String url;
    private String method;
    private String json;
    private long connectTimeout;
    private long readTimeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleHttpClientJob >>>> taskId: {},  taskName: {}, taskDesc: {}", getTaskId(), getTaskName(), getTaskDesc());
        HttpUtils.Method httpMethod =  HttpUtils.Method.valueOf(method);
        if(httpMethod == HttpUtils.Method.GET){
            try {
//                String result = HttpUtil.get(url, connectTimeout, readTimeout);
//                log.info("HttpUtil.get result -> {}", result);
                HttpUtils.get(url, true,
                        (response, throwable) ->
                                log.info("HttpUtil.get response.statusCode -> {}, response.body -> {}", response.statusCode(), response.body()), connectTimeout, readTimeout);
            } catch (Exception e) {
                log.error("HttpUtil.get error. url -> {}, exception -> {}", url, e.getMessage(), e);
            }
        }
        else if (httpMethod == HttpUtils.Method.POST) {
            try {
                String result = HttpUtils.post(url, json, connectTimeout, readTimeout);
                log.info("HttpUtil.post result -> {}", result);
            } catch (Exception e) {
                log.error("HttpUtil.post error. url -> {}, exception -> {}", url, e.getMessage(), e);
            }
        }
    }
}
