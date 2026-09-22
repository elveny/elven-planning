package site.elven.planning.scheduler.web.controller.test;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.scheduler.web.model.request.test.TestReq;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@RestController
@RequestMapping("/web/v1/test")
public class TestController {

    private ObjectMapper mapObjectMapper;

    @Value("${testing}")
    private String testing;

    @PostConstruct
    public void init() {
        mapObjectMapper = JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .build();
    }

    @RequestMapping("/mapper/map")
    public Map<String, Object> mapperMap(@RequestBody Map<String, Object> req) {
        return mapObjectMapper.convertValue(req, new TypeReference<>() {
        });
    }

    @RequestMapping("/mapper/object")
    public Map<String, Object> mapperObject(@RequestBody TestReq req) {
        return mapObjectMapper.convertValue(req, new TypeReference<>() {
        });
    }

    @RequestMapping("/config")
    public String config() {
        return testing;
    }
}
