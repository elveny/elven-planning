package site.elven.planning.plan.web.service.task.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.elven.planning.api.web.client.scheduler.app.AppTaskClient;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;
import site.elven.planning.plan.web.service.task.AppTaskService;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AppTaskServiceImpl implements AppTaskService {
    private final AppTaskClient appTaskClient;

    public AppTaskServiceImpl(AppTaskClient appTaskClient) {
        this.appTaskClient = appTaskClient;
    }

    @SentinelResource(value = "queryTasks", blockHandler = "queryTasksBlockHandler")
    @Override
    public List<AppTaskRsp> queryTasks(String appCode, String secret) {
        return appTaskClient.queryTasks(appCode, secret);
    }

    public List<AppTaskRsp> queryTasksBlockHandler(String appCode, String secret, BlockException ex) {
        log.error("queryTasksBlockHandler: {}, {}", ex.getMessage(), ex.getRuleLimitApp());
        return Collections.emptyList();
    }
}
