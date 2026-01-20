package site.elven.planning.plan.web.service.template.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.api.common.enums.plan.TemplateType;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.plan.request.template.TemplateInfoReq;
import site.elven.planning.api.web.model.plan.response.template.TemplateDetailRsp;
import site.elven.planning.api.web.model.plan.response.template.TemplateInfoRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.plan.common.exception.ExceptionCode;
import site.elven.planning.plan.common.exception.PlanException;
import site.elven.planning.plan.web.dal.template.TemplateInfoRepository;
import site.elven.planning.plan.web.mapstruct.template.TemplateInfoMapStruct;
import site.elven.planning.plan.web.mapstruct.template.TemplatePropertyMapStruct;
import site.elven.planning.plan.web.model.entity.template.TemplateInfo;
import site.elven.planning.plan.web.model.entity.template.TemplateProperty;
import site.elven.planning.plan.web.service.template.TemplateInfoService;
import site.elven.planning.plan.web.service.template.TemplatePropertyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TemplateInfoServiceImpl extends ServiceImpl<TemplateInfo, Long, TemplateInfoRepository> implements TemplateInfoService {

    private final TemplatePropertyService templatePropertyService;
    @Autowired(required = true)
    private TemplateInfoMapStruct templateInfoMapStruct;
    @Autowired(required = true)
    private TemplatePropertyMapStruct templatePropertyMapStruct;

    public TemplateInfoServiceImpl(TemplatePropertyService templatePropertyService) {
        this.templatePropertyService = templatePropertyService;
//        this.templateInfoMapStruct = templateInfoMapStruct;
    }

    @Override
    public TemplateDetailRsp save(TemplateInfoReq templateInfoReq) {
        // TODO: 模板类型根据用户类型确定是system还是user（业务限制：用户不能添加system的）
//        TemplateType templateType = templateInfoReq.getTemplateType();
        TemplateInfo templateInfo = templateInfoMapStruct.req2Entity(templateInfoReq);
        List<TemplateProperty> properties = templatePropertyMapStruct.req2Entity(templateInfoReq.getProperties());
        templateInfo = this.save(templateInfo);
        Long templateId = templateInfo.getId();
        log.info("保存计划模板信息：{}", templateId);

        if(!CollectionUtils.isEmpty(properties)){
            for (TemplateProperty property : properties){
                property.setDataId(templateId);
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
            templatePropertyService.saveAll(properties);
        }

        // 对象转换
        TemplateDetailRsp templateDetailRsp = entity2Detail(templateInfo);
        // 属性设置
        templateDetailRsp.setProperties(templatePropertyMapStruct.entity2Rsp(properties));

        return templateDetailRsp;
    }

    @Override
    public TemplateDetailRsp update(long templateId, TemplateInfoReq templateInfoReq) {
        Optional<TemplateInfo> optional = repository.findById(templateId);

        TemplateInfo templateInfo = optional.orElseThrow(() -> new PlanException(ExceptionCode.PLAN_DEFAULT.getCode(), "Template not found"));

        // 修改主数据
        templateInfo.setUsername(templateInfoReq.getUsername());
        templateInfo.setTemplateName(templateInfoReq.getTemplateName());
        templateInfo.setTemplateIntro(templateInfoReq.getTemplateIntro());
        templateInfo.setTemplateType(templateInfoReq.getTemplateType());
        templateInfo.setEnabled(templateInfoReq.getEnabled());
        templateInfo = repository.save(templateInfo);

        // 逻辑删除属性
        int result = templatePropertyService.logicDeleteAllInBatch(templatePropertyService.findByDataId(templateId));

        // 保存属性
        List<TemplateProperty> properties = templatePropertyMapStruct.req2Entity(templateInfoReq.getProperties());

        if(!CollectionUtils.isEmpty(properties)){
            for (TemplateProperty property : properties){
                property.setDataId(templateId);
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
            templatePropertyService.saveAll(properties);
        }

        // 对象转换
        TemplateDetailRsp templateDetailRsp = entity2Detail(templateInfo);
        // 属性设置
        templateDetailRsp.setProperties(templatePropertyMapStruct.entity2Rsp(properties));

        return templateDetailRsp;
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateDetailRsp detail(long templateId) {
        enableDeleteFilter();
        Optional<TemplateInfo> optional = repository.findByIdAndNotDeleted(templateId);
        if(optional.isPresent()){
            TemplateInfo templateInfo = optional.get();
            List<TemplateProperty> properties = templatePropertyService.findByDataId(templateId);

            // 对象转换
            TemplateDetailRsp templateDetailRsp = entity2Detail(templateInfo);
            // 属性设置
            templateDetailRsp.setProperties(templatePropertyMapStruct.entity2Rsp(properties));

            return templateDetailRsp;
        }
        return null;
    }

    @Override
    public PageRsp<TemplateInfoRsp> list(TemplateInfoReq templateInfoReq) {
        String username = templateInfoReq.getUsername();
        String templateName = templateInfoReq.getTemplateName();
        String templateIntro = templateInfoReq.getTemplateIntro();
        TemplateType templateType = templateInfoReq.getTemplateType();
        Boolean enabled = templateInfoReq.getEnabled();

        TemplateInfo search = new TemplateInfo();
        Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers = new HashMap<>();
        if(StringUtils.hasText(username)){
            search.setUsername(username);
            matchers.put("username", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(templateName)){
            search.setTemplateName(templateName);
            matchers.put("templateName", match -> match.startsWith().ignoreCase());
        }
        if(StringUtils.hasText(templateIntro)){
            search.setTemplateIntro(templateIntro);
            matchers.put("templateIntro", match -> match.startsWith().ignoreCase());
        }
        if(templateType != null){
            search.setTemplateType(templateType);
        }
        if(enabled != null){
            search.setEnabled(enabled);
        }

        // 分页查询数据
        PageRsp<TemplateInfo> pageRsp = list(search, templateInfoReq.getPageNumber(), templateInfoReq.getPageSize(), null, null, matchers);
        // 转换数据
        List<TemplateInfoRsp> list = pageRsp.getContent().stream().map(templateInfoMapStruct::entity2Rsp).toList();
        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), list);
    }

    @Override
    public int logicDeleteById(Long id) {
        repository.findById(id).ifPresent(
                templateInfo -> {
                    if(TemplateType.system.equals(templateInfo.getTemplateType())){
                        throw new PlanException(ExceptionCode.PLAN_TEMPLATE_SYSTEM_TEMPLATES_CANNOT_BE_DELETED.getCode(), "System templates cannot be deleted");
                    }
                    super.logicDelete(templateInfo);
                    templatePropertyService.logicDeleteAllInBatch(templatePropertyService.findByDataId(id));
                }
        );
        return 1;
    }

    private TemplateDetailRsp entity2Detail(TemplateInfo templateInfo){
        TemplateInfoRsp templateInfoRsp = templateInfoMapStruct.entity2Rsp(templateInfo);
        return templateInfoMapStruct.toDetail(templateInfoRsp);
    }
}
