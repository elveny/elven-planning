package site.elven.planning.experiment.web.service.test;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.*;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class MicrometerTestService {
    private final MeterRegistry registry;
    private final Counter counter;
    private final Queue<Object> taskQueue = new LinkedBlockingQueue<>();
    // 必须持有强引用！否则被 GC 后 Gauge 会报告 NaN
    private final Gauge queueSizeGauge;

    private final Timer objectQueryTimer;

    private final DistributionSummary responseSize;
    private final DistributionSummary hitRatio;
    private final DistributionSummary batchSize;



    public MicrometerTestService(MeterRegistry registry) {
        this.registry = registry;
        this.counter = Counter.builder("test.counter")
                .description("A test counter")
                .tag("tagKey", "tagValue")
                .register(registry);

        this.queueSizeGauge = Gauge.builder("test.gauge.task.queue.size", taskQueue, Queue::size)
                .description("Number of pending tasks in queue")
                .tag("queue", "default")
                .register(registry);

        this.objectQueryTimer = Timer.builder("test.timer.object.query.duration")
                .description("Time taken to query object from DB")
                .tag("db", "test")
//                .serviceLevelObjectives(Duration.ofMillis(500), Duration.ofMillis(900), Duration.ofMillis(950), Duration.ofMillis(990))
                .publishPercentiles(0.5, 0.9, 0.95, 0.99)
                .register(registry);

        this.responseSize = DistributionSummary.builder("test.distribution-summary.http.response.size")
                .description("HTTP response body size in bytes")
                .baseUnit("bytes")                    // 提升可移植性
                .tag("api", "user")
                .register(registry);

        // 比值范围是 [0,1]，用 scale(100) 放大到 [0,100]
        this.hitRatio = DistributionSummary.builder("test.distribution-summary.cache.hit.ratio")
                .description("Cache hit ratio")
                .baseUnit("percent")
                .scale(100)
                .serviceLevelObjectives(70, 80, 90)   // SLO 边界
                .register(registry);

        this.batchSize = DistributionSummary.builder("test.distribution-summary.batch.process.size")
                .description("Number of items processed per batch")
                .baseUnit("items")
                .publishPercentileHistogram()          // 启用百分位直方图
                .minimumExpectedValue(1.0)              // 钳制范围，减少桶数量
                .maximumExpectedValue(1000.0)
                .register(registry);



    }

    public void incrementCounter() {
        counter.increment();
    }

    @Counted(value = "test.counter", description = "A test counter", extraTags = {"tagKey1", "tagValue1"})
    public void anotherIncrementCounter() {
        return ;
    }

    public void addTask(Object task) {
        taskQueue.add(task);
    }

    public void pollTask() {
        taskQueue.poll();
    }

    public Object findObject(Long id) {
        return objectQueryTimer.record(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new Object();
        });
    }

    public Object getObject() {
        Timer.Sample sample = Timer.start(registry);
        try {
            int timeout = new Random().nextInt(10000);
            if(timeout % 2 == 0){
                throw new Exception("timeout");
            }
            TimeUnit.MILLISECONDS.sleep(timeout);
            sample.stop(Timer.builder("test.timer.http.user.request")
                    .tag("status", "200")
                    .register(registry));
            return new Object();

        } catch (InterruptedException e) {
            sample.stop(Timer.builder("test.timer.http.user.request")
                    .tag("status", "500")
                    .register(registry));

            throw new RuntimeException(e);
        } catch (Exception e) {
            sample.stop(Timer.builder("test.timer.http.user.request")
                    .tag("status", "500")
                    .tag("exception", e.getClass().getName())
                    .register(registry));
            throw new RuntimeException(e);
        }
    }

    @Timed(value = "test.timer.order.process.duration",
            description = "Order processing time",
            percentiles = {0.5, 0.95, 0.99})
    public Object processOrder() {
        int timeout = new Random().nextInt(10000);
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
            return new Object();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Object recordResponse() {
        int random = new Random().nextInt(10000);
        responseSize.record(random);
        return new Object();
    }

    public Map<String, Double> recordRatio() {
        double ratio = new Random().nextDouble();
        // 传入 0.85，实际记录为 85
        hitRatio.record(ratio);
        return Map.of("ratio", ratio);
    }

    public Map<String, Integer> recordBatch() {
        int itemCount = new Random().nextInt(1000);
        batchSize.record(itemCount);
        return Map.of("itemCount", itemCount);
    }

    @Timed(value = "test.timer.long.task.aws.scrape", longTask = true)
    @Scheduled(fixedDelay = 10000)   // 每 6 分钟执行一次
    public void scrapeResources() {
        // 从 AWS 拉取实例、卷、自动伸缩组等资源
        // 正常情况下几分钟完成，AWS 故障时可能耗时很久
        System.out.println("Scraping AWS resources... This may take a while. Please wait.");
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
