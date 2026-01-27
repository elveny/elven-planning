package site.elven.planning.plan.web.model.entity.template;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;

@Entity
@Table(name = "plan_template_property")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TemplateProperty extends BasePropertyEntity {
}
