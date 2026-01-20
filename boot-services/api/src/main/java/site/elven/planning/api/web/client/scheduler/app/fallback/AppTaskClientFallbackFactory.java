package site.elven.planning.api.web.client.scheduler.app.fallback;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import site.elven.planning.api.web.client.scheduler.app.AppTaskClient;
import site.elven.planning.api.web.model.base.response.base.BaseResultRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppAddTaskReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppTaskRsp;

import java.util.List;

@Slf4j
@Component
public class AppTaskClientFallbackFactory implements FallbackFactory<AppTaskClient> {
    @Override
    public AppTaskClient create(Throwable cause) {
        log.info("[fall-back] AppTaskClientFallbackFactory...Throwable: {}", cause.getMessage(), cause);
        return new AppTaskClient() {
            @Override
            public AppTaskRsp addTask(AppAddTaskReq req) {
                return null;
            }

            @Override
            public List<AppTaskRsp> queryTasks(String appCode, String secret) {
                log.info("[fall-back] AppTaskClientFallbackFactory.queryTasks...");
                return Lists.newArrayList();
            }

            @Override
            public AppTaskRsp queryTask(String appCode, String appSecret, String taskName) {
                return null;
            }

            @Override
            public BaseResultRsp startTask(String appCode, String secret, String taskName) {
                return null;
            }

            @Override
            public BaseResultRsp pauseTask(String appCode, String secret, String taskName) {
                return null;
            }

            @Override
            public BaseResultRsp stopTask(String appCode, String secret, String taskName) {
                return null;
            }

            @Override
            public BaseResultRsp cancelTask(String appCode, String secret, String taskName) {
                return null;
            }
        };
    }
}
