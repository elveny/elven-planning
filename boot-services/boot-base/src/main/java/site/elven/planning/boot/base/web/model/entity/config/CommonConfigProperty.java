package site.elven.planning.boot.base.web.model.entity.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;

@Entity
@Table(name = "common_config_property")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class CommonConfigProperty extends BasePropertyEntity {
}
