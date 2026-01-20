package site.elven.planning.boot.base.web.service.config;

import site.elven.planning.api.web.model.base.request.config.CommonConfigInfoReq;
import site.elven.planning.boot.base.web.dal.config.CommonConfigInfoRepository;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigInfo;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.api.web.model.base.response.config.CommonConfigDetailRsp;
import site.elven.planning.boot.base.web.service.base.IService;

import java.util.List;

public interface CommonConfigInfoService extends IService<CommonConfigInfo, Long, CommonConfigInfoRepository> {
    CommonConfigInfo save(CommonConfigInfo configInfo, List<CommonConfigProperty> properties);
    CommonConfigDetailRsp detail(CommonConfigInfoReq commonConfigInfoReq);
}
