package site.elven.planning.experiment.web.controller.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.experiment.web.service.test.MicrometerTestService;

import java.util.Map;

@RestController
@RequestMapping("/web/v1/test/micrometer")
public class MicrometerTestController {

    private final MicrometerTestService micrometerTestService;

    public MicrometerTestController(MicrometerTestService micrometerTestService) {
        this.micrometerTestService = micrometerTestService;
    }

    @RequestMapping("/counter/increment")
    public void incrementCounter() {
        micrometerTestService.incrementCounter();
    }

    @RequestMapping("/counter/another-increment")
    public void anotherIncrementCounter() {
        micrometerTestService.anotherIncrementCounter();
    }

    @RequestMapping("/gauge/add-task")
    public void addTask() {
        micrometerTestService.addTask(new Object());
    }

    @RequestMapping("/gauge/poll-task")
    public void pollTask() {
        micrometerTestService.pollTask();
    }

    @RequestMapping("/timer/find-object/{id}")
    public Object findObject(@PathVariable("id") long id) {
        return micrometerTestService.findObject(id);
    }

    @RequestMapping("/timer/get-object")
    public Object getObject() {
        return micrometerTestService.getObject();
    }

    @RequestMapping("/timer/process-order")
    public Object processOrder() {
        return micrometerTestService.processOrder();
    }

    @RequestMapping("/distribution-summary/record-response")
    public Object recordResponse() {
        return micrometerTestService.recordResponse();
    }

    @RequestMapping("/distribution-summary/record-ratio")
    public Map<String, Double> recordRatio() {
        return micrometerTestService.recordRatio();
    }

    @RequestMapping("/distribution-summary/record-batch")
    public Map<String, Integer> recordBatch() {
        return micrometerTestService.recordBatch();
    }
}
