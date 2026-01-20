package site.elven.planning.boot.base.web.model.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import site.elven.planning.boot.base.common.constant.DatabaseDefaultConstants;

import java.io.Serial;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@FilterDef(name = "delete_filter", defaultCondition = "deleted = false", parameters = @ParamDef(name = "deleted", type = Boolean.class))
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class BaseEntity implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "deleted_id")
    private Long deletedId;
    @Column(name = "version")
    private Integer version;

}
