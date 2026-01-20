package site.elven.planning.user.test.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ObjectMapperTest {

    private static ObjectMapper mapObjectMapper;

    @BeforeAll
    public static void setup(){
        mapObjectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
    }

    @Test
    public void test1(){
        Map<String, Object> req = Map.of("task_id", 1L, "task_name", "test");
        Map<String, Object> rsp = mapObjectMapper.convertValue(req, new TypeReference<>() {
        });
        System.out.println(rsp);
    }

    @Test
    public void test2() throws JsonProcessingException {
        Map<String, Object> source = Map.of("task_id", 1L, "task_name", "test");
        String json = mapObjectMapper.writeValueAsString(source);
        Map<String, Object> target = mapObjectMapper.readValue(json, new TypeReference<>() {
        });
        System.out.println(target);
    }
}
