package site.elven.planning.experiment.web.controller.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.experiment.web.service.sentinel.SentinelTestService;

@RestController
@RequestMapping("/web/v1/test/sentinel")
public class SentinelTestController {
    private final SentinelTestService sentinelTestService;

    public SentinelTestController(SentinelTestService sentinelTestService) {
        this.sentinelTestService = sentinelTestService;
    }

    @RequestMapping("/test-entry-api")
    public String testEntryApi() {
        return sentinelTestService.testEntryApi();
    }

    @RequestMapping("/test-annotation-api")
    public String testAnnotation() {
        return sentinelTestService.testAnnotation();
    }

    @RequestMapping("/test-http-rest")
    public String testHttpRest() {
        return "Sentinel Test Http Rest";
    }

    @RequestMapping("/test-degrade-annotation")
    public String testDegradeAnnotation() {
        return sentinelTestService.testDegradeAnnotation();
    }

}
