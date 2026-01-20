package site.elven.planning.plan.web.service.template;

import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.boot.base.web.service.base.IService;
import site.elven.planning.plan.web.dal.template.TemplateInfoRepository;
import site.elven.planning.plan.web.model.entity.template.TemplateInfo;
import site.elven.planning.api.web.model.plan.request.template.TemplateInfoReq;
import site.elven.planning.api.web.model.plan.response.template.TemplateDetailRsp;
import site.elven.planning.api.web.model.plan.response.template.TemplateInfoRsp;

public interface TemplateInfoService extends IService<TemplateInfo, Long, TemplateInfoRepository> {
    /**
     * 保存计划模板
     * @param templateInfoReq 模板请求参数
     * @return 任务模板
     */
    TemplateDetailRsp save(TemplateInfoReq templateInfoReq);

    /**
     * 更新计划模板
     * @param templateId 模板ID
     * @param templateInfoReq 模板请求参数
     * @return 模板详情
     */
    TemplateDetailRsp update(long templateId, TemplateInfoReq templateInfoReq);

    /**
     * 获取计划模板详情
     * @param templateId 任务模板ID
     * @return 任务模板详情
     */
    TemplateDetailRsp detail(long templateId);

    PageRsp<TemplateInfoRsp> list(TemplateInfoReq templateInfoReq);

    int logicDeleteById(Long id);

}
