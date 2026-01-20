package site.elven.planning.plan.web.mapstruct.template;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.plan.web.model.entity.template.TemplateInfo;
import site.elven.planning.api.web.model.plan.request.template.TemplateInfoReq;
import site.elven.planning.api.web.model.plan.response.template.TemplateDetailRsp;
import site.elven.planning.api.web.model.plan.response.template.TemplateInfoRsp;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateInfoMapStruct extends BaseMapStruct<TemplateInfo, TemplateInfoReq, TemplateInfoRsp> {
    TemplateDetailRsp toDetail (TemplateInfoRsp templateInfoRsp);
}
