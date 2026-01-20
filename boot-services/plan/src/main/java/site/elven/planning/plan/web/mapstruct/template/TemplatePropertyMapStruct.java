package site.elven.planning.plan.web.mapstruct.template;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import site.elven.planning.api.web.model.plan.request.template.TemplatePropertyReq;
import site.elven.planning.api.web.model.plan.response.template.TemplatePropertyRsp;
import site.elven.planning.boot.base.web.mapstruct.base.BaseMapStruct;
import site.elven.planning.plan.web.model.entity.template.TemplateProperty;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplatePropertyMapStruct extends BaseMapStruct<TemplateProperty, TemplatePropertyReq, TemplatePropertyRsp> {
}
