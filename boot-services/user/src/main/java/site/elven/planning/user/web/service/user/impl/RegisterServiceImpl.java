package site.elven.planning.user.web.service.user.impl;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import site.elven.planning.api.web.model.user.request.RegisterReq;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.user.web.mapstruct.UserMapStruct;
import site.elven.planning.user.web.model.entity.user.User;
import site.elven.planning.user.web.service.user.RegisterService;
import site.elven.planning.user.web.service.user.UserService;

import java.time.LocalDateTime;

@Component
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapStruct userMapStruct;

    @Override
    public UserRsp createUser(RegisterReq registerReq){
        User user = new User();
        user.setCode(IdUtil.fastSimpleUUID());
        user.setUsername(registerReq.getUsername());
        String password = registerReq.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(registerReq.getEmail());
        user.setPhoneNumber(registerReq.getPhoneNumber());
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setDateOfBirth(registerReq.getDateOfBirth());
        user.setGender(registerReq.getGender());
        user.setType(registerReq.getType());
        user.setExpireTime(LocalDateTime.now().plusYears(100));
        user = userService.save(user);
        return userMapStruct.entity2Rsp(user);
    }
}
