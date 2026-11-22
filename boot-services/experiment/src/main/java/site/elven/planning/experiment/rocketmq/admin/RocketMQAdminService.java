package site.elven.planning.experiment.rocketmq.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.protocol.admin.ConsumeStats;
import org.apache.rocketmq.remoting.protocol.admin.OffsetWrapper;
import org.apache.rocketmq.remoting.protocol.body.ClusterInfo;
import org.apache.rocketmq.remoting.protocol.body.ConsumerConnection;
import org.apache.rocketmq.remoting.protocol.body.ConsumerRunningInfo;
import org.apache.rocketmq.remoting.protocol.body.GroupList;
import org.apache.rocketmq.remoting.protocol.route.BrokerData;
import org.apache.rocketmq.remoting.protocol.route.TopicRouteData;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RocketMQAdminService {

    private final RocketMQProperties properties;

    private final DefaultMQAdminExt adminExt;

    public RocketMQAdminService(RocketMQProperties properties, DefaultMQAdminExt adminExt) {
        this.properties = properties;
        this.adminExt = adminExt;
    }

    // ---------- Topic 管理 ----------

    /** 创建 Topic，队列数同时作用于读和写 */
    public boolean createTopic(String cluster, String topic, int queueNum) {
        try {
            TopicConfig config = new TopicConfig(topic);
            config.setReadQueueNums(queueNum);
            config.setWriteQueueNums(queueNum);
            adminExt.createAndUpdateTopicConfig(cluster, config);
            log.info("创建 Topic 成功: cluster={}, topic={}, queues={}", cluster, topic, queueNum);
            return true;
        } catch (Exception e) {
            log.error("创建 Topic 失败: topic={}", topic, e);
            return false;
        }
    }

    // ---------- 消费进度查询 ----------

    /** 查询指定消费者组的消费进度，返回每个队列的 Offset 快照 */
    public ConsumeStats queryConsumeStats(String consumerGroup) {
        try {
            ConsumeStats stats = adminExt.examineConsumeStats(consumerGroup);
            if (stats != null && stats.getOffsetTable() != null) {
                for (Map.Entry<MessageQueue, OffsetWrapper> entry : stats.getOffsetTable().entrySet()) {
                    OffsetWrapper w = entry.getValue();
                    log.debug("队列: {}, 消费位点: {}, Broker位点: {}, 最后存储时间: {}",
                            entry.getKey(), w.getConsumerOffset(), w.getBrokerOffset(), w.getLastTimestamp());
                }
            }
            return stats;
        } catch (Exception e) {
            log.error("查询消费进度失败: group={}", consumerGroup, e);
            return null;
        }
    }

    // ---------- 位点重置 ----------

    /** 将指定消费者组在 Topic 上的位点重置到某个时间戳（毫秒） */
    public boolean resetOffsetByTimestamp(String topic, String consumerGroup, long timestamp) {
        try {
            adminExt.resetOffsetNew(consumerGroup, topic, timestamp);
            log.info("位点重置成功: topic={}, group={}, timestamp={}", topic, consumerGroup, timestamp);
            return true;
        } catch (Exception e) {
            log.error("位点重置失败: topic={}, group={}", topic, consumerGroup, e);
            return false;
        }
    }

    public boolean createSubscribe(String topic, String consumerGroup){
        DefaultMQPushConsumer defaultMQPushConsumer = null;
        try {
            defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup,
                    new AclClientRPCHook(
                            new SessionCredentials(properties.getConsumer().getAccessKey(), properties.getConsumer().getSecretKey())
                    )
            );
            defaultMQPushConsumer.setNamesrvAddr(properties.getNameServer());
            defaultMQPushConsumer.registerMessageListener(
                    (MessageListenerConcurrently) (msgs, context) -> ConsumeConcurrentlyStatus.RECONSUME_LATER
            );
            defaultMQPushConsumer.subscribe(topic, "*");
            defaultMQPushConsumer.start();
            for (int i = 0; i < 15; i++) {
                try{
                    ConsumeStats consumeStats = queryConsumeStats(consumerGroup);
                    if(consumeStats != null && consumeStats.getOffsetTable() != null){
                        log.info("[{}]消费者组 {} 订阅主题 {} 成功", i, consumerGroup, topic);
                        return true;
                    }
                }
                catch (Exception ignored){
                    log.warn("[{}]消费者组 {} 订阅主题 {} 失败，正在重试...", i, consumerGroup, topic);
                }

                TimeUnit.SECONDS.sleep(2); // Sleep for 2 seconds
            }
            return false;
        } catch (MQClientException | InterruptedException e) {
            log.error("消费者组 {} 订阅主题 {} 失败", consumerGroup, topic, e);
            return false;
        }
        finally {
            if(defaultMQPushConsumer != null){
                defaultMQPushConsumer.shutdown();
                log.info("消费者组 {} 订阅主题 {} 完成，已关闭消费者", consumerGroup, topic);
            }
        }
    }

    // ---------- 路由与集群信息 ----------

    /** 查询 Topic 路由信息，用于排查 “No topic route info” 类问题 */
    public TopicRouteData queryTopicRoute(String topic) {
        try {
            return adminExt.examineTopicRouteInfo(topic);
        } catch (MQClientException e) {
            log.error("查询 Topic 路由失败: topic={}, code={}", topic, e.getResponseCode(), e);
            return null;
        } catch (RemotingException | InterruptedException e) {
            log.error("查询 Topic 路由失败: topic={}, exception={}", topic, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /** 查询集群信息，确认 Broker 注册状态 */
    public ClusterInfo queryClusterInfo() {
        try {
            return adminExt.examineBrokerClusterInfo();
        } catch (Exception e) {
            log.error("查询集群信息失败", e);
            return null;
        }
    }

    /** 删除 Topic：先删 Broker，再删 NameServer */
    public boolean deleteTopic(String cluster, String topic) {
        try {
            // 获取集群中所有 Master Broker 地址
            ClusterInfo clusterInfo = adminExt.examineBrokerClusterInfo();
            Set<String> masterAddrs = new HashSet<>();
            for (BrokerData brokerData : clusterInfo.getBrokerAddrTable().values()) {
                String masterAddr = brokerData.getBrokerAddrs().get(MixAll.MASTER_ID);
                if (masterAddr != null) {
                    masterAddrs.add(masterAddr);
                }
            }
            if (!masterAddrs.isEmpty()) {
                adminExt.deleteTopicInBroker(masterAddrs, topic);
            }
            adminExt.deleteTopicInNameServer(null, topic);
            log.info("删除 Topic 成功: topic={}", topic);
            return true;
        } catch (Exception e) {
            log.error("删除 Topic 失败: topic={}", topic, e);
            return false;
        }
    }

    /** 查询哪些消费者组订阅了指定 Topic */
    public GroupList queryTopicConsumeByWho(String topic) {
        try {
            return adminExt.queryTopicConsumeByWho(topic);
        } catch (Exception e) {
            log.error("查询 Topic 消费者失败: topic={}", topic, e);
            return null;
        }
    }

    /** 查询消费者组的在线连接信息（clientId、地址、订阅关系） */
    public ConsumerConnection queryConsumerConnection(String consumerGroup) {
        try {
            return adminExt.examineConsumerConnectionInfo(consumerGroup);
        } catch (Exception e) {
            log.error("查询消费者连接失败: group={}", consumerGroup, e);
            return null;
        }
    }

    /** 查询某个具体消费者客户端的运行信息（队列分配、位点明细） */
    public ConsumerRunningInfo queryConsumerRunningInfo(String consumerGroup, String clientId) {
        try {
            return adminExt.getConsumerRunningInfo(consumerGroup, clientId, false);
        } catch (Exception e) {
            log.error("查询消费者运行信息失败: group={}, clientId={}", consumerGroup, clientId, e);
            return null;
        }
    }

    /** 按 MessageId 查看消息详情 */
    public MessageExt viewMessage(String topic, String msgId) {
        try {
            return adminExt.viewMessage(topic, msgId);
        } catch (Exception e) {
            log.error("按 MessageId 查询消息失败: topic={}, msgId={}", topic, msgId, e);
            return null;
        }
    }

    /** 按业务 Key 查询消息（返回的是查询结果包装类） */
    public QueryResult queryMessageByKey(String topic, String key) {
        try {
            // 部分版本可能使用 queryMessageByUniqKey，视 5.3.1 的实际 API 而定
            return adminExt.queryMessage(topic, key, 32, 0, System.currentTimeMillis());
        } catch (Exception e) {
            log.error("按 Key 查询消息失败: topic={}, key={}", topic, key, e);
            return null;
        }
    }
}