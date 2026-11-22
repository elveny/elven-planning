package site.elven.planning.experiment.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.elven.planning.common.util.date.DateUtils;

import java.util.concurrent.atomic.AtomicLong;

@Component
@EnableScheduling
public class TimestampProducer implements CommandLineRunner {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("TimestampProducer 已启动，开始每秒发送消息...");
    }

    /**
     * fixedRate = 1000：以上一次任务"计划开始时间"为基准，每 1000ms 触发一次。
     * 如果单次发送耗时超过 1 秒，下一次会立即补发，不会累积漂移。
     */
    @Scheduled(fixedRate = 100000)
    public void sendEverySecond() {
        long now = System.currentTimeMillis();
        long currentSeq = seq.incrementAndGet();

        // 消息体：包含序号和当前时间戳，便于后续核对发送节奏
        String body = String.format("seq=%d,timestamp=%d,time=%s",
                currentSeq, now, DateUtils.millisToString(now));

        try {
            SendResult result = rocketMQTemplate.syncSend("test-timestamp-topic", body);
            System.out.printf("[发送] seq=%d, timestamp=%d, msgId=%s, status=%s%n",
                    currentSeq, now, result.getMsgId(), result.getSendStatus());
        } catch (Exception e) {
            // 发送失败不中断定时任务，记录日志后继续下一轮
            System.err.printf("[发送失败] seq=%d, timestamp=%d, 原因: %s%n",
                    currentSeq, now, e.getMessage());
        }
    }
}
