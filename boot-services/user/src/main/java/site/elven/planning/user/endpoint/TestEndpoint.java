package site.elven.planning.user.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Map;

@Endpoint(id = "test")
@Component
public class TestEndpoint {
    private static final Map<String, String> map = Map.of("k1", "v1", "k2", "v2");
    @ReadOperation
    public Map<String, String> get(){
        return map;
    }

    /**
     * 访问： http://localhost:8800/elven-planning/manage/test/k1
     * @param key key参数
     * @return 值
     */
    @ReadOperation
    public String get(@Selector String key){
        return map.get(key);
    }

}
