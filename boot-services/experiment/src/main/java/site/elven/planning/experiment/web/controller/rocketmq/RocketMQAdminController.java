package site.elven.planning.experiment.web.controller.rocketmq;

import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.admin.ConsumeStats;
import org.apache.rocketmq.remoting.protocol.body.ClusterInfo;
import org.apache.rocketmq.remoting.protocol.body.ConsumerConnection;
import org.apache.rocketmq.remoting.protocol.body.ConsumerRunningInfo;
import org.apache.rocketmq.remoting.protocol.body.GroupList;
import org.apache.rocketmq.remoting.protocol.route.TopicRouteData;
import org.springframework.web.bind.annotation.*;
import site.elven.planning.experiment.rocketmq.admin.RocketMQAdminService;

@RestController
@RequestMapping("/api/rocketmq/admin")
public class RocketMQAdminController {
    private final RocketMQAdminService adminService;

    public RocketMQAdminController(RocketMQAdminService adminService) {
        this.adminService = adminService;
    }

    // ---------- Topic ----------

    @PostMapping("/topic")
    public Boolean createTopic(@RequestParam String cluster,
                                       @RequestParam String topic,
                                       @RequestParam(defaultValue = "4") int queueNum) {
        return adminService.createTopic(cluster, topic, queueNum);
    }

    @DeleteMapping("/topic")
    public Boolean deleteTopic(@RequestParam String cluster,
                                       @RequestParam String topic) {
        return adminService.deleteTopic(cluster, topic);
    }

    @GetMapping("/topic/route")
    public TopicRouteData topicRoute(@RequestParam String topic) {
        return adminService.queryTopicRoute(topic);
    }

    @GetMapping("/topic/consumers")
    public GroupList topicConsumers(@RequestParam String topic) {
        return adminService.queryTopicConsumeByWho(topic);
    }

    // ---------- 消费进度与位点 ----------

    @GetMapping("/consume-stats")
    public ConsumeStats consumeStats(@RequestParam String consumerGroup) {
        return adminService.queryConsumeStats(consumerGroup);
    }

    @PostMapping("/offset/reset")
    public Boolean resetOffset(@RequestParam String topic,
                               @RequestParam String consumerGroup,
                               @RequestParam long timestamp) {
        return adminService.resetOffsetByTimestamp(topic, consumerGroup, timestamp);
    }

    @PostMapping("/subscribe")
    public Boolean createSubscribe(@RequestParam String topic,
                                   @RequestParam String consumerGroup) {
        return adminService.createSubscribe(topic, consumerGroup);
    }

    // ---------- 消费者连接与运行信息 ----------

    @GetMapping("/consumer/connection")
    public ConsumerConnection consumerConnection(@RequestParam String consumerGroup) {
        return adminService.queryConsumerConnection(consumerGroup);
    }

    @GetMapping("/consumer/running-info")
    public ConsumerRunningInfo consumerRunningInfo(@RequestParam String consumerGroup,
                                                   @RequestParam String clientId) {
        return adminService.queryConsumerRunningInfo(consumerGroup, clientId);
    }

    // ---------- 消息查询 ----------

    @GetMapping("/message/by-id")
    public MessageExt messageById(@RequestParam String topic, @RequestParam String msgId) {
        return adminService.viewMessage(topic, msgId);
    }

    @GetMapping("/message/by-key")
    public QueryResult messageByKey(@RequestParam String topic,
                                    @RequestParam String key) {
        return adminService.queryMessageByKey(topic, key);
    }

    // ---------- 集群 ----------

    @GetMapping("/cluster")
    public ClusterInfo clusterInfo() {
        return adminService.queryClusterInfo();
    }
}
