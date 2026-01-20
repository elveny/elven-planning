package site.elven.planning.user.web.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.elven.planning.api.common.enums.user.UserStatus;
import site.elven.planning.api.common.enums.user.UserType;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.user.request.UserReq;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.user.web.dal.user.UserRepository;
import site.elven.planning.user.web.mapstruct.UserMapStruct;
import site.elven.planning.user.web.model.entity.user.User;
import site.elven.planning.user.web.service.user.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<User, Long, UserRepository> implements UserService, UserDetailsService {

    @Autowired
    private UserMapStruct userMapStruct;

    public UserRsp findByUsername(String username) {
        return userMapStruct.entity2Rsp(getRepository().findByUsername(username));
    }

    public UserRsp findByEmail(String email) {
        return userMapStruct.entity2Rsp(getRepository().findByEmail(email));
    }

    public UserRsp findByPhoneNumber(String phoneNumber) {
        return userMapStruct.entity2Rsp(getRepository().findByPhoneNumber(phoneNumber));
    }

    @Override
    public UserRsp detail(Long id) {
        return userMapStruct.entity2Rsp(findById(id));
    }

    @Override
    public PageRsp<UserRsp> list(UserReq searchReq) {
        String code = searchReq.getCode();
        String username = searchReq.getUsername();
        String email = searchReq.getEmail();
        String phoneNumber = searchReq.getPhoneNumber();
        String firstName = searchReq.getFirstName();
        String lastName = searchReq.getLastName();
        Date dateOfBirth = searchReq.getDateOfBirth();
        Character gender = searchReq.getGender();
        UserType type = searchReq.getType();
        UserStatus status = searchReq.getStatus();

        User search = new User();
        Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers = new HashMap<>();
        if(StringUtils.hasText(code)){
            search.setCode(code);
        }
        if(StringUtils.hasText(username)){
            search.setUsername(username);
            matchers.put("username", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(email)){
            search.setEmail(email);
            matchers.put("email", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(phoneNumber)){
            search.setPhoneNumber(phoneNumber);
            matchers.put("phoneNumber", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(firstName)){
            search.setFirstName(firstName);
            matchers.put("firstName", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(lastName)){
            search.setLastName(lastName);
            matchers.put("lastName", match -> match.startsWith().ignoreCase());
        }
        if(gender != null){
            search.setGender(gender);
        }
        if(type != null){
            search.setType(type);
        }
        if(status != null){
            search.setStatus(status);
        }

        // 分页查询数据
        PageRsp<User> pageRsp = list(search, searchReq.getPageNumber(), searchReq.getPageSize(), null, null, matchers);

        // 转换数据
        List<UserRsp> list = pageRsp.getContent().stream().map(userMapStruct::entity2Rsp).collect(Collectors.toList());
        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), list);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getRepository().findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
