package site.elven.planning.experiment.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import site.elven.planning.common.util.date.DateUtils;

import java.nio.charset.StandardCharsets;

@Component
@RocketMQMessageListener(
        topic = "test-timestamp-topic",
        consumerGroup = "test-timestamp-consumer-group",
        selectorExpression = "*",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.CONCURRENTLY
)
@Lazy(false)
public class TimestampConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        // 消息到达消费者时的本地时间，可与消息中的 timestamp 对比，观察链路延迟
        long receiveTime = System.currentTimeMillis();
        System.out.printf("[消费] 收到: %s, 接收时间=%d(%s)%n", new String(message.getBody(), StandardCharsets.UTF_8), receiveTime, DateUtils.millisToString(receiveTime));
    }
}
