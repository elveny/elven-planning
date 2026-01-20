package site.elven.planning.user.configuration.listener;

import com.alibaba.cloud.nacos.annotation.NacosConfigListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserNacosConfigListener {
    @NacosConfigListener(dataId = "application-common.yaml", group = "user", initNotify = true)
    public void common(String commonConfig) {
        log.info("UserNacosConfigListener common: {}", commonConfig);
    }

    @NacosConfigListener(dataId = "application-property.yaml", group = "user", initNotify = true)
    public void property(String property) {
        log.info("UserNacosConfigListener property: {}", property);
    }

}
