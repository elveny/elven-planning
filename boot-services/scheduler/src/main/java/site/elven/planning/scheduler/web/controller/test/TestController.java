package site.elven.planning.scheduler.web.controller.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.scheduler.web.model.request.test.TestReq;

import java.util.Map;

@RestController
@RequestMapping("/web/v1/test")
public class TestController {

    private ObjectMapper mapObjectMapper;

    @Value("${testing}")
    private String testing;

    @PostConstruct
    public void init() {
        mapObjectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
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
