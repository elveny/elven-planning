package site.elven.planning.plan.web.controller.template;

import org.springframework.web.bind.annotation.*;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.plan.request.template.TemplateInfoReq;
import site.elven.planning.api.web.model.plan.response.template.TemplateDeleteRsp;
import site.elven.planning.api.web.model.plan.response.template.TemplateDetailRsp;
import site.elven.planning.api.web.model.plan.response.template.TemplateInfoRsp;
import site.elven.planning.plan.web.service.template.TemplateInfoService;

@RestController
@RequestMapping("/web/v1/template")
public class TemplateController {
    private final TemplateInfoService templateInfoService;
    public TemplateController(TemplateInfoService templateInfoService) {
        this.templateInfoService = templateInfoService;
    }

    @RequestMapping("/list")
    public PageRsp<TemplateInfoRsp> list(@RequestBody TemplateInfoReq templateInfoReq) {
        return templateInfoService.list(templateInfoReq);
    }

    @RequestMapping("/create")
    public TemplateDetailRsp create(@RequestBody TemplateInfoReq templateInfoReq) {
        return templateInfoService.save(templateInfoReq);
    }

    @RequestMapping(value = "/{template_id}", method = RequestMethod.PUT)
    public TemplateDetailRsp update(@PathVariable("template_id") long templateId, @RequestBody TemplateInfoReq templateInfoReq) {
        return templateInfoService.update(templateId, templateInfoReq);
    }

    @RequestMapping(value = "/{template_id}", method = RequestMethod.GET)
    public TemplateDetailRsp detail(@PathVariable("template_id") long templateId) {
        return templateInfoService.detail(templateId);
    }

    @RequestMapping(value = "/{template_id}", method = RequestMethod.DELETE)
    public TemplateDeleteRsp delete(@PathVariable("template_id") long templateId) {
        templateInfoService.logicDeleteById(templateId);
        return new TemplateDeleteRsp(true);
    }
}
