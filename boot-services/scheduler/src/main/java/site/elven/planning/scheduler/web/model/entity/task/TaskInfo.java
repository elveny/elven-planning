package site.elven.planning.scheduler.web.model.entity.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.api.common.enums.scheduler.TaskStatus;
import site.elven.planning.api.common.enums.scheduler.TaskType;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;

@Entity
@Table(name = "scheduler_task_info")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class TaskInfo extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "username", length = 64)
    private String username;
    @Column(name = "task_type", length = 16)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    @Column(name = "task_name", length = 128)
    private String taskName;
    @Column(name = "task_desc", length = 128)
    private String taskDesc;
    @Column(name = "task_status", length = 16)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @Column(name = "group_name", length = 128)
    private String groupName;
    @Column(name = "job_name", length = 128)
    private String jobName;
    @Column(name = "trigger_name", length = 128)
    private String triggerName;
    @Column(name = "job_class_name", length = 128)
    private String jobClassName;
    @Column(name = "cron_expression", length = 128)
    private String cronExpression;
    @Column(name = "enabled")
    private Boolean enabled = true;

}
