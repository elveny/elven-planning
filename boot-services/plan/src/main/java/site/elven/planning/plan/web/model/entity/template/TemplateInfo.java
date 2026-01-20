package site.elven.planning.plan.web.model.entity.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;
import site.elven.planning.api.common.enums.plan.TemplateType;

@Entity
@Table(name = "plan_template_info")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateInfo extends BaseEntity {
    @Column(name = "username", length = 64)
    private String username;
    @Column(name = "template_code", length = 128)
    private String templateCode;
    @Column(name = "template_name", length = 128)
    private String templateName;
    @Column(name = "template_intro", length = 1024)
    private String templateIntro;
    @Column(name = "template_type", length = 32)
    @Enumerated(EnumType.STRING)
    private TemplateType templateType;
    @Column(name = "is_default")
    private Boolean isDefault;
    @Column(name = "enabled")
    private Boolean enabled;
}
