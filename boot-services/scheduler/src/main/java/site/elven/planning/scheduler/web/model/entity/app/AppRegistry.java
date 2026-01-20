package site.elven.planning.scheduler.web.model.entity.app;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.api.common.enums.scheduler.AppStatus;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;

@Entity
@Table(name = "scheduler_app_registry")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class AppRegistry extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "app_code", length = 128)
    private String appCode;
    @Column(name = "app_name", length = 128)
    private String appName;
    @Column(name = "app_secret", length = 128)
    private String appSecret;
    @Column(name = "app_desc", length = 1024)
    private String appDesc;
    @Column(name = "app_status", length = 16)
    @Enumerated(EnumType.STRING)
    private AppStatus appStatus;
    @Column(name = "group_name", length = 128)
    private String groupName;

}
