package site.elven.planning.scheduler.web.service.app;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.request.app.AppRegistryReq;
import site.elven.planning.api.web.model.scheduler.response.app.AppRegistryRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.scheduler.web.dal.app.AppRegistryRepository;
import site.elven.planning.scheduler.web.model.entity.app.AppRegistry;

public interface AppRegisterService extends IService<AppRegistry, Long, AppRegistryRepository> {
    AppRegistryRsp register(AppRegistryReq req);
    PageRsp<AppRegistryRsp> list(AppRegistryReq req);
    AppRegistryRsp detail(long id);
    AppRegistryRsp detail(String code, String secret);
    boolean disable(long id);
    boolean enable(long id);
}
