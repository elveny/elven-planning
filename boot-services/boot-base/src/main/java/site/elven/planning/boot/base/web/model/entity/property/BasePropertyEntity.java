package site.elven.planning.boot.base.web.model.entity.property;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.base.CommonConfigPropertyType;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BasePropertyEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "data_id")
    private Long dataId;
    @Column(name = "property_code", length = 128)
    private String propertyCode;
    @Column(name = "property_name", length = 128)
    private String propertyName;
    @Column(name = "property_desc", length = 256)
    private String propertyDesc;
    @Column(name = "property_type", length = 128)
    @Enumerated(EnumType.STRING)
    private CommonConfigPropertyType propertyType;
    @Column(name = "property_value", length = 1024)
    private String propertyValue;
    @Column(name = "property_extend")
    private String propertyExtend;
    @Column(name = "property_sort")
    private Integer propertySort;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "enabled")
    private Boolean enabled = true;

}
