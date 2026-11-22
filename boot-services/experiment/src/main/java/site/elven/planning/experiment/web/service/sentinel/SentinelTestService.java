package site.elven.planning.experiment.web.service.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SentinelTestService {

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("test-api-resource1"); // 对应 资源名
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        List<FlowRule> flowRuleList = FlowRuleManager.getRules();
        flowRuleList.stream().forEach(r -> log.info("Rule: {}", r));
    }

    public String testEntryApi() {
        try(Entry entry = SphU.entry("test-api-resource1")) {
            log.info("Resource is normal...");
        } catch (BlockException e) {
            log.warn("Blocked by Sentinel: {}", e.getMessage());
        }
        return "Sentinel Test Api";
    }

    @SentinelResource(value = "test-annotation-resource1")
    public String testAnnotation() {
        return "Sentinel Test Annotation";
    }

    @SentinelResource(value = "test-degrade-annotation-resource1", blockHandler = "handleBlockException")
    public String testDegradeAnnotation() {
        int sleepMs = new Random().nextInt(10000);
        try {
            TimeUnit.MILLISECONDS.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Sleep for " + sleepMs + " ms";
    }

    public String handleBlockException(BlockException e) {
        log.warn("Degrade by Sentinel: {}", e.getMessage());
        return "Degrade by Sentinel: " + e.getClass().getSimpleName();
    }
}
