package site.elven.planning.user.web.service.test.impl;

import org.springframework.stereotype.Service;
import site.elven.planning.user.web.service.test.TestService;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test1() {
        int i = 1/0 ;
        return "";
    }

    @Override
    public String test2() {
        throw new RuntimeException("test2");
    }
}
