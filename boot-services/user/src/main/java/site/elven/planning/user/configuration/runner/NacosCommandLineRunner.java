package site.elven.planning.user.configuration.runner;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Executable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class NacosCommandLineRunner implements CommandLineRunner {
    private final NacosConfigManager nacosConfigManager;

    public NacosCommandLineRunner(NacosConfigManager nacosConfigManager) {
        this.nacosConfigManager = nacosConfigManager;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("<<<<<<<<<<<<<<<< NacosCommandLineRunner started. >>>>>>>>>>>>>>>>");
        nacosConfigManager.getConfigService().addListener("application-property.yaml", "user", new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newFixedThreadPool(1);
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("NacosConfigManager receiveConfigInfo: {}", configInfo);
            }
        });
    }
}
