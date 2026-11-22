package site.elven.planning.experiment.rocketmq.admin;

import lombok.Data;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.admin")
public class MQAdminConfig {

    private String namesrvAddr;
    private String adminGroup = "springboot-admin-group";
    private long timeoutMillis = 5000L;
    private String accessKey;
    private String secretKey;

    @Bean(destroyMethod = "shutdown")
    public DefaultMQAdminExt defaultMQAdminExt() throws Exception {
        DefaultMQAdminExt admin;
        if (StringUtils.hasText(accessKey) && StringUtils.hasText(secretKey)) {
            RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
            admin = new DefaultMQAdminExt(rpcHook, timeoutMillis);
        } else {
            admin = new DefaultMQAdminExt(timeoutMillis);
        }
        admin.setNamesrvAddr(namesrvAddr);
        admin.setAdminExtGroup(adminGroup);
        admin.start(); // 启动管理客户端，建立与 NameServer/Broker 的长连接
        return admin;
    }
}