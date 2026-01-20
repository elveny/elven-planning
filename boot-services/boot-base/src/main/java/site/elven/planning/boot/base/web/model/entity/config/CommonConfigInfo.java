package site.elven.planning.boot.base.web.model.entity.config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.api.common.enums.base.CommonConfigSourceCode;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;

@Entity
@Table(name = "common_config_info")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class CommonConfigInfo extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "username", length = 64)
    private String username;
    @Column(name = "source_code", length = 128)
    @Enumerated(EnumType.STRING)
    private CommonConfigSourceCode sourceCode;
    @Column(name = "source_id")
    private Long sourceId;
    @Column(name = "remark", length = 128)
    private String remark;
    @Column(name = "enabled")
    private Boolean enabled = true;

}
