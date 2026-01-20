package site.elven.planning.scheduler.web.service.app.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.elven.planning.api.common.enums.scheduler.AppStatus;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppRegistryReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.scheduler.web.dal.app.AppRegistryRepository;
import site.elven.planning.scheduler.web.mapstruct.app.AppRegistryMapStruct;
import site.elven.planning.scheduler.web.model.entity.app.AppRegistry;
import site.elven.planning.scheduler.web.service.app.AppRegisterService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AppRegisterServiceImpl extends ServiceImpl<AppRegistry, Long, AppRegistryRepository> implements AppRegisterService {
    private final AppRegistryMapStruct appRegistryMapStruct;

    public AppRegisterServiceImpl(AppRegistryMapStruct appRegistryMapStruct) {
        this.appRegistryMapStruct = appRegistryMapStruct;
    }

    @Override
    public AppRegistryRsp register(AppRegistryReq req) {
        AppRegistry appRegistry = appRegistryMapStruct.req2Entity(req);
        // 默认应用名称为 应用编码
        if(!StringUtils.hasText(appRegistry.getAppName())){
            appRegistry.setAppName(appRegistry.getAppCode());
        }
        if(!StringUtils.hasText(appRegistry.getGroupName())){
            appRegistry.setGroupName(appRegistry.getAppCode());
        }
        if(!StringUtils.hasText(appRegistry.getAppSecret())){
            appRegistry.setAppSecret(UUID.randomUUID().toString());
        }
        if(appRegistry.getAppStatus() == null){
            appRegistry.setAppStatus(AppStatus.active);
        }
        return appRegistryMapStruct.entity2Rsp(save(appRegistry));
    }

    @Override
    public PageRsp<AppRegistryRsp> list(AppRegistryReq req) {
        String appCode = req.getAppCode();
        String appName = req.getAppName();
        String appDesc = req.getAppDesc();
        AppStatus appStatus = req.getAppStatus();
        String groupName = req.getGroupName();

        AppRegistry search = new AppRegistry();
        Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers = new HashMap<>();
        if(!StringUtils.hasText(appCode)){
            search.setAppCode(appCode);
        }
        if(!StringUtils.hasText(appName)){
            search.setAppName(appName);
            matchers.put("appName", match -> match.startsWith().ignoreCase());
        }
        if(!StringUtils.hasText(appDesc)){
            search.setAppDesc(appDesc);
            matchers.put("appDesc", match -> match.startsWith().ignoreCase());
        }
        if(appStatus != null){
            search.setAppStatus(appStatus);
        }
        if(!StringUtils.hasText(groupName)){
            search.setGroupName(groupName);
        }

        PageRsp<AppRegistry> pageRsp = list(search, req.getPageNumber(), req.getPageSize(), null, null, matchers);

        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), appRegistryMapStruct.entity2Rsp(pageRsp.getContent()));
    }

    @Override
    public AppRegistryRsp detail(long id) {
        return appRegistryMapStruct.entity2Rsp(findById(id));
    }

    @Override
    public AppRegistryRsp detail(String code, String secret) {
        AppRegistry search = new AppRegistry();
        search.setAppCode(code);
        search.setAppSecret(secret);
        return appRegistryMapStruct.entity2Rsp(findOne(search));
    }

    @Override
    public boolean disable(long id) {
        AppRegistry appRegistry = findById(id);
        appRegistry.setAppStatus(AppStatus.inactive);
        save(appRegistry);
        return true;
    }

    @Override
    public boolean enable(long id) {
        AppRegistry appRegistry = findById(id);
        appRegistry.setAppStatus(AppStatus.active);
        save(appRegistry);
        return true;
    }
}
