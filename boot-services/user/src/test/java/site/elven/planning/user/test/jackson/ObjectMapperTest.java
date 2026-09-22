package site.elven.planning.user.test.jackson;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

public class ObjectMapperTest {

    private static ObjectMapper mapObjectMapper;

    @BeforeAll
    public static void setup(){
        mapObjectMapper = JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .build();
    }

    @Test
    public void test1(){
        Map<String, Object> req = Map.of("task_id", 1L, "task_name", "test");
        Map<String, Object> rsp = mapObjectMapper.convertValue(req, new TypeReference<>() {
        });
        System.out.println(rsp);
    }

    @Test
    public void test2() throws JacksonException {
        Map<String, Object> source = Map.of("task_id", 1L, "task_name", "test");
        String json = mapObjectMapper.writeValueAsString(source);
        Map<String, Object> target = mapObjectMapper.readValue(json, new TypeReference<>() {
        });
        System.out.println(target);
    }
}
