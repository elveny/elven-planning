package site.elven.planning.experiment.sentinel;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TestSentinelDegradeSimulation extends Simulation {
    // 1. 定义 HTTP 协议配置
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8900/planning-experiment/web/v1/test/sentinel") // 你的服务地址
            .acceptHeader("application/json");

    // 2. 定义场景：访问 /test-api
    ScenarioBuilder degradeScenario = scenario("Test sentinel degrade annotation")
            .exec(http("Request Test API")
                    .get("/test-degrade-annotation")
                    .check(status().is(200))); // 检查响应状态码为 200

    // 3. 配置注入策略：在 10 秒内逐步增加到 10 个用户
    {
        setUp(
                degradeScenario.injectOpen(
                        constantUsersPerSec(5).during(60))
        ).protocols(httpProtocol)
                .assertions( // 添加断言，定义测试通过标准
                        global().responseTime().max().lt(2000), // 最大响应时间小于 2 秒
                        global().successfulRequests().percent().is(100.0) // 成功率为 100%
                );

    }

}
