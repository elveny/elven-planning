package site.elven.planning.experiment.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.hook.ConsumeMessageContext;
import org.apache.rocketmq.client.hook.ConsumeMessageHook;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class PushConsumerExample {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-timestamp-consumer-group");
        consumer.setNamesrvAddr("192.168.135.129:9876");
        consumer.subscribe("test-timestamp-topic", "*");
        consumer.registerConsumeMessageHook(new ConsumeMessageHook(){

            @Override
            public String hookName() {
                return "hook1";
            }

            @Override
            public void consumeMessageBefore(ConsumeMessageContext context) {
                System.out.println("consumeMessageBefore");
            }

            @Override
            public void consumeMessageAfter(ConsumeMessageContext context) {
                System.out.println("consumeMessageAfter");
            }
        });
        consumer.registerMessageListener(new MessageListenerConcurrently(){

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs){
                    System.out.println(msg);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Consumer started.");

    }
}
