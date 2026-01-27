package site.elven.planning.boot.common.web.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.api.web.model.base.request.config.CommonConfigInfoReq;
import site.elven.planning.api.web.model.base.response.config.CommonConfigDetailRsp;
import site.elven.planning.boot.base.common.constant.CommonConstants;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.boot.common.web.dal.config.CommonConfigInfoRepository;
import site.elven.planning.boot.common.web.mapstruct.config.CommonConfigInfoMapStruct;
import site.elven.planning.boot.common.web.mapstruct.config.CommonConfigPropertyMapStruct;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigInfo;
import site.elven.planning.boot.common.web.model.entity.config.CommonConfigProperty;
import site.elven.planning.boot.common.web.service.config.CommonConfigInfoService;
import site.elven.planning.boot.common.web.service.config.CommonConfigPropertyService;

import java.util.List;

@Slf4j
@Service
public class CommonConfigInfoServiceImpl extends ServiceImpl<CommonConfigInfo, Long, CommonConfigInfoRepository> implements CommonConfigInfoService {

    private final CommonConfigPropertyService commonConfigPropertyService;

    @Autowired
    private CommonConfigInfoMapStruct commonConfigInfoMapStruct;

    @Autowired
    private CommonConfigPropertyMapStruct commonConfigPropertyMapStruct;

    public CommonConfigInfoServiceImpl(CommonConfigPropertyService commonConfigPropertyService) {
        this.commonConfigPropertyService = commonConfigPropertyService;
    }

    @Override
    public CommonConfigInfo save(CommonConfigInfo configInfo, List<CommonConfigProperty> properties) {
        String username = configInfo.getUsername();
        if(username == null){
            configInfo.setUsername(CommonConstants.COMMON_DEFAULT_SYSTEM_USER);
        }
        configInfo = this.save(configInfo);
        log.info("保存配置信息：{}", configInfo.getId());

        for (CommonConfigProperty property : properties) {
            property.setDataId(configInfo.getId());
            String propertyCode = property.getPropertyCode();
            String propertyName = property.getPropertyName();
            CommonConfigPropertyType propertyType = property.getPropertyType();
            if(propertyName == null){
                property.setPropertyName(propertyCode);
            }
            if(propertyType == null){
                property.setPropertyType(CommonConfigPropertyType.STRING);
            }
        }
        commonConfigPropertyService.saveAll(properties);
        return configInfo;
    }

    @Override
    public CommonConfigDetailRsp detail(CommonConfigInfoReq commonConfigInfoReq) {
        CommonConfigInfo commonConfig = repository.findBySourceCodeAndSourceId(commonConfigInfoReq.getSourceCode(), commonConfigInfoReq.getSourceId());
        if(commonConfig == null){
            return new CommonConfigDetailRsp(null, null);
        }
        List<CommonConfigProperty> commonConfigProperties = commonConfigPropertyService.findByDataId(commonConfig.getId());
        return new CommonConfigDetailRsp(
                commonConfigInfoMapStruct.entity2Rsp(commonConfig),
                commonConfigPropertyMapStruct.entity2Rsp(commonConfigProperties)
        );
    }
}
