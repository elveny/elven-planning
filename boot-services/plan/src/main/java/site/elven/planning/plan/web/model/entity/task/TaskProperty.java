package site.elven.planning.plan.web.model.entity.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;

@Entity
@Table(name = "plan_task_property")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TaskProperty extends BasePropertyEntity {
}
