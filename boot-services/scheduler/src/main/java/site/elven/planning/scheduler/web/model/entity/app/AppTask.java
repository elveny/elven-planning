package site.elven.planning.scheduler.web.model.entity.app;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.api.common.enums.scheduler.AppStatus;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;

@Entity
@Table(name = "scheduler_app_task")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class AppTask extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "app_code", length = 128)
    private String appCode;
    @Column(name = "group_name", length = 128)
    private String groupName;
    @Column(name = "task_name", length = 128)
    private String taskName;
    @Column(name = "task_desc", length = 128)
    private String taskDesc;
    @Column(name = "task_status", length = 16)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @Column(name = "params")
    private String params;
    @Column(name = "task_id")
    private Long taskId;

}
